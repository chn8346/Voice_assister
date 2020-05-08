package com.example.phoneui;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class file_writer {
    private FileOutputStream writer = null;
    private FileInputStream reader = null;

    private BufferedWriter buff_write = null;
    private BufferedReader buff_read = null;

    private String fileName = "VoiceAssistantConfigData";

    private Context context_;

    private int dir_bit = 8;

    public file_writer(Context context)
    {
        context_ = context;
    }

    public void save_data(String key, String content)
    {
        try {
            writer = context_.openFileOutput(fileName, Context.MODE_PRIVATE);
            buff_write = new BufferedWriter(new OutputStreamWriter(writer));

            int num = findKey_num(key);
            int coor_num, line_num, len;
            line_num = num & 0x0ff;
            num = num>>dir_bit;
            coor_num = num & 0x0ff;
            num = num>>dir_bit;
            len = num & 0x0ff;

            if(coor_num == -1) {
                buff_write.write(key + ",");
                buff_write.write(content + "\n");
            }else{
                ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(buff_write != null)
                {
                    buff_write.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String read_data(String[] key, int n)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < n; i++)
        {
            stringBuilder.append(findKey(key[i])).append("\n");
        }
        return stringBuilder.toString();
    }

    public String read_data(String key)
    {
        if(key.equals("*"))
        {
            return findKey("null", true);
        }
        else
        {
            return findKey(key);
        }
    }

    private int findKey_num(String key)
    {
        int line_num = 0;
        int coor_num = -1;
        int length = -1;
        try{
            reader = context_.openFileInput(fileName);
            buff_read = new BufferedReader(new InputStreamReader(reader));
            String line = "";

            while ((line = buff_read.readLine())!= null)
            {
                if(line.contains(key))
                {
                    coor_num = line.indexOf(",") + 1;
                    length = line.length() - coor_num + 1;
                    break;
                }
                line_num++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(buff_read != null)
            {
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        int ans = length;
        ans = ans << dir_bit;
        ans = ans | coor_num;
        ans = ans << dir_bit;
        ans = ans | line_num;
        return ans;
    }

    private String findKey(String key)
    {
        return findKey(key, false);
    }

    private String findKey(String key, boolean all_in)
    {
        String ans = "find_null";
        StringBuilder stringBuilder = new StringBuilder();
        try{
            reader = context_.openFileInput(fileName);
            buff_read = new BufferedReader(new InputStreamReader(reader));
            String line = "";
            if(all_in)
            {
                while ((line = buff_read.readLine())!= null)
                {
                    stringBuilder.append(line).append("\n");
                }
            }
            else
            {
                while ((line = buff_read.readLine())!= null)
                {
                    if(line.contains(key))
                    {
                        ans = line.substring(line.indexOf(",") + 1);
                        break;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(buff_read != null)
            {
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }


        if(all_in) {
            return stringBuilder.toString();
        }
        else {
            return ans;
        }
    }

}
