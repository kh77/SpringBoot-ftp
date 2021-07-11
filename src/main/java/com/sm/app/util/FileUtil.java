package com.sm.app.util;

import com.sm.app.model.Product;

import java.io.*;

public class FileUtil {

    public static ByteArrayInputStream getProductBytes() {
        ByteArrayInputStream inputStream = null;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            Product product = new Product();
            product.setName("Johnson");
            product.setId(100L);

            oos.writeObject(product);
            inputStream = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
        return inputStream;
    }
}
