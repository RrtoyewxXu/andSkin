package com.rrtoyewx.andskinlibrary.listener;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public interface IDataOperation {
    void saveStringMessage(String key, String message);

    String getStringMessage(String key, String defaultValue);

    void saveBooleanMessage(String key, boolean message);

    boolean getBooleanMessage(String key, boolean defaultValue);

    void saveIntMessage(String key, int message);

    int getIntMessage(String key, int defaultValue);

    void saveLongMessage(String key, long message);

    long getLongMessage(String key, long defaultValue);

    void saveFloatMessage(String key, float message);

    float getFloatMessage(String key, float defaultValue);

    void saveDoubleMessage(String key, double message);

    double getDoubleMessage(String key, double defaultValue);

    class SimpleDateOperation implements IDataOperation {

        @Override
        public void saveStringMessage(String key, String message) {

        }

        @Override
        public String getStringMessage(String key, String defaultValue) {
            return defaultValue;
        }

        @Override
        public void saveBooleanMessage(String key, boolean message) {

        }

        @Override
        public boolean getBooleanMessage(String key, boolean defaultValue) {
            return defaultValue;
        }

        @Override
        public void saveIntMessage(String key, int message) {

        }

        @Override
        public int getIntMessage(String key, int defaultValue) {
            return defaultValue;
        }

        @Override
        public void saveLongMessage(String key, long message) {

        }

        @Override
        public long getLongMessage(String key, long defaultValue) {
            return defaultValue;
        }

        @Override
        public void saveFloatMessage(String key, float message) {

        }

        @Override
        public float getFloatMessage(String key, float defaultValue) {
            return defaultValue;
        }

        @Override
        public void saveDoubleMessage(String key, double message) {

        }

        @Override
        public double getDoubleMessage(String key, double defaultValue) {
            return defaultValue;
        }
    }
}
