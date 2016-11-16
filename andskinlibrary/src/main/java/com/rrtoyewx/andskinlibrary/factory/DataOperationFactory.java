package com.rrtoyewx.andskinlibrary.factory;

import android.content.Context;

import com.rrtoyewx.andskinlibrary.helper.SharedPreferencesHelper;
import com.rrtoyewx.andskinlibrary.interfaces.IDataManipulation;
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

    public abstract IDataManipulation createDataOperation(Context context);


    static class DataOperationFactoryImp extends DataOperationFactory {

        @Override
        public IDataManipulation createDataOperation(Context context) {
            SkinL.d("生成 shared preferences data operation");
            return new SharedPreferencesHelper(context);
        }
    }


}
