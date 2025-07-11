package utils;

import jakarta.servlet.jsp.JspWriter;

public class JsFunction {
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = ""
					+"<script>"
					+" alert('"+msg+"');"
					+" location.href='" + url+"';"
					+"</script>";
			out.println(script);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = ""
					+"<script>"
					+" alert('"+msg+"');"
					+" histroy.back();"
					+"</script>";
			out.println(script);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
