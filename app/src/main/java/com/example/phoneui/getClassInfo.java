package com.example.phoneui;

public class getClassInfo {
    private StringBuffer band = new StringBuffer();

    public getClassInfo()
    {
        this.band.setLength(0);
    }

    public void update_data(String result)
    {
        this.band.append(result);
    }

    public void update_all_data(String result)
    {
        _clear();
        update_data(result);
    }

    public void _clear()
    {
        this.band.setLength(0);
    }

    public String download_result()
    {
        return this.band.toString();
    }

}
