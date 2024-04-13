<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>

<body>
    <form action="j_security_check" METHOD="POST">
        <table>
            <tr>
                <td>
                    Login:<input type="text" name="j_username" />
                </td>
            </tr>
            <tr>
                <td>
                    Password:<input type="text" name="j_password" />
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Submit" />
                </td>
            </tr>
        </table>
    </form>
</body>

</html>