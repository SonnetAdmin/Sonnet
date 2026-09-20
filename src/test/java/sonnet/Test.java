package sonnet;

public class Test
{
    public static void main(String[] args)
    {
        String gender = "maLe";
        String intermediate = gender.toLowerCase();
        String camelCase = intermediate.substring(0, 1).toUpperCase() + intermediate.substring(1);
        System.out.println(camelCase);
    }
}
