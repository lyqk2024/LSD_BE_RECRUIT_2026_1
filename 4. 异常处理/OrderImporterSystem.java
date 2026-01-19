import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Test4：订单导入校验系统
 *
 * 本题考察范围：
 * - 异常：自定义异常、异常链、catch 粒度、fail-fast vs fail-safe
 * - 正则：校验 orderId/userId（建议 Pattern 复用）
 * - 枚举：失败原因用 FailureReason 枚举统一管理
 * - 统计：失败原因汇总建议使用 EnumMap
 *
 * TODO：
 * 1) 拆分为多文件（Main / Order / OrderImporter / FailureReason / 自定义异常等）
 * 2) 补全 importOrders：单行失败不影响整体（循环内 catch）：
 *      2.1) parseLine：按规则校验并抛出携带 FailureReason 的自定义异常
 *      2.2) 输出失败明细与失败原因汇总
 */
public class OrderImporterSystem {

    public static void main(String[] args) {
        List<String> lines = List.of(
                "O1001,U01,99.50",
                "O1002,U02,-3.00",
                "O1003,,10.00",
                "BAD_LINE",
                "O1004,U04,abc",
                "O12,U12345,18.88",
                "O1005,U05,18.88"
        );

        OrderImporter importer = new OrderImporter();
        ImportResult result = importer.importOrders(lines);

        System.out.println("=== Import Summary ===");
        System.out.println("Success Count: " + result.getOrders().size());
        System.out.println("Fail Count: " + result.getFailures().size());

        System.out.println("\n=== Success Orders ===");
        for (Order o : result.getOrders()) {
            System.out.println(o);
        }

        System.out.println("\n=== Failures ===");
        for (FailureRecord fr : result.getFailures()) {
            System.out.println(fr);
        }

        System.out.println("\n=== Failure Reason Summary ===");
        for (Map.Entry<FailureReason, Integer> e : result.getFailureReasonSummary().entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

/**
 * 订单实体
 */
class Order {
    private final String orderId;
    private final String userId;
    private final BigDecimal amount;

    public Order(String orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }

    @Override
    public String toString() {
        return "Order{orderId='" + orderId + "', userId='" + userId + "', amount=" + amount + "}";
    }
}

/**
 * 失败原因枚举：统一管理失败类型
 */
enum FailureReason {
    INVALID_FORMAT("Line format is invalid"),
    INVALID_ORDER_ID("OrderId format is invalid"),
    INVALID_USER_ID("UserId format is invalid"),
    INVALID_AMOUNT("Amount is invalid"),
    NEGATIVE_AMOUNT("Amount cannot be negative"),
    UNKNOWN_ERROR("Unknown error");

    private final String defaultMessage;

    FailureReason(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}

/**
 * 导入失败记录：记录行号、原始内容、失败原因枚举、详细信息
 */
class FailureRecord {
    private final int lineNo;
    private final String rawLine;
    private final FailureReason reason;
    private final String detailMessage;

    public FailureRecord(int lineNo, String rawLine, FailureReason reason, String detailMessage) {
        this.lineNo = lineNo;
        this.rawLine = rawLine;
        this.reason = reason;
        this.detailMessage = detailMessage;
    }

    public int getLineNo() { return lineNo; }
    public String getRawLine() { return rawLine; }
    public FailureReason getReason() { return reason; }
    public String getDetailMessage() { return detailMessage; }

    @Override
    public String toString() {
        return "FailureRecord{" +
                "lineNo=" + lineNo +
                ", rawLine='" + rawLine + '\'' +
                ", reason=" + reason +
                ", detail='" + detailMessage + '\'' +
                '}';
    }
}

/**
 * 导入结果：成功订单 + 失败记录 + 失败原因汇总
 */
class ImportResult {
    private final List<Order> orders;
    private final List<FailureRecord> failures;
    private final Map<FailureReason, Integer> failureReasonSummary;

    public ImportResult(List<Order> orders, List<FailureRecord> failures, Map<FailureReason, Integer> failureReasonSummary) {
        this.orders = orders;
        this.failures = failures;
        this.failureReasonSummary = failureReasonSummary;
    }

    public List<Order> getOrders() { return orders; }
    public List<FailureRecord> getFailures() { return failures; }
    public Map<FailureReason, Integer> getFailureReasonSummary() { return failureReasonSummary; }
}

/**
 * 自定义异常：携带 FailureReason，支持异常链（cause）
 *
 * TODO：为什么用 Checked / Unchecked？这里如何选择？
 */
class OrderImportException extends Exception {
    private final FailureReason reason;

    // 可补充更多构造方法，例如：只传 reason 使用默认 message 等
    // 但现在给出的方法已经足以完成后续题目
    public OrderImportException(FailureReason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public OrderImportException(FailureReason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    public FailureReason getReason() {
        return reason;
    }
}

/**
 * 核心逻辑在这里
 */
class OrderImporter {

    // 正则规则（要求使用正则表达式校验）
    private static final Pattern ORDER_ID_PATTERN = Pattern.compile("O\\d{4}");
    private static final Pattern USER_ID_PATTERN  = Pattern.compile("U\\d{2,5}");

    /**
     * 导入订单：要求“单行失败不影响整体”
     *
     * TODO：
     * 1) 遍历每行，调用 parseLine
     * 2) 成功：加入 orders
     * 3) 失败：记录 FailureRecord（行号、原始行、FailureReason、detailMessage）
     * 4) 失败原因统计：使用 EnumMap<FailureReason, Integer> 并 merge 计数
     * 5) catch 粒度：优先捕获 OrderImportException；避免直接 catch Exception 吞掉细节
     */
    public ImportResult importOrders(List<String> lines) {

        List<Order> orders = new ArrayList<>();
        List<FailureRecord> failures = new ArrayList<>();
        Map<FailureReason, Integer> reasonSummary = new EnumMap<>(FailureReason.class);

        // TODO：补全导入逻辑（循环内 catch，fail-safe）
        // for (int i = 0; i < lines.size(); i++) { ... }

        return new ImportResult(orders, failures, reasonSummary);
    }

    /**
     * 解析单行：将一行字符串转成 Order
     *
     * 规则：
     * 1) 必须为 3 段，用逗号分隔
     * 2) orderId 必须匹配 O\\d{4}
     * 3) userId 必须匹配 U\\d{2,5}
     * 4) amount 必须是数字且 >= 0（BigDecimal）
     *
     * TODO：
     * - 正则校验失败抛 OrderImportException，并设置对应 FailureReason
     * - amount 解析失败（NumberFormatException）要保留 cause
     */
    private Order parseLine(String line) throws OrderImportException {

        // TODO：1) 分割并校验段数
        // String[] parts = line.split(",");
        // if (parts.length != 3) throw new OrderImportException(FailureReason.INVALID_FORMAT, ...);

        // TODO：2) trim 后取字段
        // String orderId = ...
        // String userId  = ...
        // String amountStr = ...

        // TODO：3) 用 Pattern 校验 orderId / userId
        // if (!ORDER_ID_PATTERN.matcher(orderId).matches()) throw ...
        // if (!USER_ID_PATTERN.matcher(userId).matches()) throw ...

        // TODO：4) BigDecimal 解析 amount + 非负校验（异常链）
        // try { BigDecimal amount = new BigDecimal(amountStr); ... }
        // catch (NumberFormatException e) { throw new OrderImportException(FailureReason.INVALID_AMOUNT, ..., e); }

        return null; // TODO：返回 new Order(...)
    }

    // TODO：可选 helper：原因计数
    // private void addReason(Map<FailureReason, Integer> summary, FailureReason reason) {
    //     summary.merge(reason, 1, Integer::sum);
    // }
}
