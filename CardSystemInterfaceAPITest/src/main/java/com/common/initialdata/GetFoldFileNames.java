package com.common.initialdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetFoldFileNames {

    public static void main(String[] args) {
        getFileName("D:\\workspace\\CardSystemInterfaceAPITest\\sql\\db_member\\general");
    }

    public static List<String> getFileName(String path) {
        File f = new File(path);
        List<String> list = new ArrayList<String>();
        if (!f.exists()) {
            System.out.println(path + " not exists");
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + "[Directory]");
            } else {
                String filename=fs.getName();                
                list.add(filename);
                System.out.println(list);
            }
        }
        return list;
    }

}
