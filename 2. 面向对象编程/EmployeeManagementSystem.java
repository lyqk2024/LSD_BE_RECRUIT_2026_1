import java.util.ArrayList;
import java.util.List;

/**
 * 测试入口类
 */
public class EmployeeManagementSystem {

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();

        employees.add(new SalesEmployee("Alice", 5000, 200000));
        employees.add(new InternEmployee("Bob", 80, 120));
        employees.add(new SalesEmployee("Cindy", 6000, 150000));

        for (Employee employee : employees) {
            employee.printEmployeeInfo();
            System.out.println("Salary: " + employee.calculateSalary());
            System.out.println("----------------------------");
        }
    }
}

interface Payable {
    /**
     * 计算薪资
     */
    double calculateSalary();
}

/**
 * 员工父类
 */
abstract class Employee implements Payable{

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    /**
     * 打印员工信息
     */
    public void printEmployeeInfo() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + this.getClass().getSimpleName());
    }

    // TODO：思考 name 为什么是 private？
    // TODO：是否需要提供 getter / setter？
    public String getName() {
        return name;
    }
}

/**
 * 销售员工
 * 工资 = baseSalary + salesAmount * rate
 */
class SalesEmployee extends Employee {

    private double baseSalary;
    private double salesAmount;

    public SalesEmployee(String name, double baseSalary, double salesAmount) {
        super(name);
        this.baseSalary = baseSalary;
        this.salesAmount = salesAmount;
    }

    // TODO：补全销售员工的工资计算逻辑
    // 提示：提成比例可自定义，如 5%
    @Override
    public double calculateSalary() {
        // TODO
        return 0;
    }

    // TODO：是否需要重写 printEmployeeInfo？
    // TODO：baseSalary 和 salesAmount 是否应该直接暴露？
}

/**
 * 实习生
 * 工资 = hourlyRate * workingHours
 */
class InternEmployee extends Employee {

    private double hourlyRate;
    private int workingHours;

    public InternEmployee(String name, double hourlyRate, int workingHours) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.workingHours = workingHours;
    }

    // TODO：补全实习生的工资计算逻辑
    @Override
    public double calculateSalary() {
        // TODO
        return 0;
    }

    // TODO：workingHours 是否需要校验（如不能超过 160）？
}
