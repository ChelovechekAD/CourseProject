package it.academy.utilities;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Enumeration;

@UtilityClass
public class DevUtils {

    public static void printRequestHeaders(HttpServletRequest request){
        System.out.println("\n///");
        Enumeration<String> test = request.getHeaderNames();
        while (test.hasMoreElements()) {
            String elem = test.nextElement();
            System.out.println(elem + " : " + request.getHeader(elem));

        }
        System.out.println("///\n");
    }


}
