package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.helper.SharedPreferencesDataOperation;
import com.rrtoyewx.andskinlibrary.listener.IDataOperation;
import com.rrtoyewx.andskinlibrary.util.SkinL;

/**
 * Created by Rrtoyewx on 2016/10/24.
 */

public abstract class DataOperationFactory {
    private DataOperationFactory() {
    }

    public static DataOperationFactory newInstance() {
        return new DataOperationFactoryImp();
    }

    public abstract IDataOperation createDataOperation(Context context);


    static class DataOperationFactoryImp extends DataOperationFactory {

        @Override
        public IDataOperation createDataOperation(Context context) {
            SkinL.d("生成 shared preferences data operation");
            return new SharedPreferencesDataOperation(context);
        }
    }


}
