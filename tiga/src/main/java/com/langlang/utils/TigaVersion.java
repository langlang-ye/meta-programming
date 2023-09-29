package com.langlang.utils;

public class TigaVersion {

    private TigaVersion() {
    }


    /**
     * 返回当前tiga 框架的版本
     *
     */
    public static String getVersion() {
        Package pkg = TigaVersion.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }

}
