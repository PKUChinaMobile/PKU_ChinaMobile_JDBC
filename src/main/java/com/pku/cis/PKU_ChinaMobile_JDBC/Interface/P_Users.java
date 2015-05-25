package com.pku.cis.PKU_ChinaMobile_JDBC.Interface;


import java.io.Serializable;

/**
 * Created by mrpen on 2015/5/25.
 */
public class P_Users implements Serializable
{
    public String userName;
    public int permission;//0-非法；1-普通；2-管理员
    public P_Users(String userName_, int permission_)
    {
        userName = userName_;
        permission = permission_;
    }
}
