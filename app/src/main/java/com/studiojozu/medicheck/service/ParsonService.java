package com.studiojozu.medicheck.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.entity.Parson;
import com.studiojozu.medicheck.repository.ColumnBase;
import com.studiojozu.medicheck.repository.ParsonRepository;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.parson.ParsonIdType;
import com.studiojozu.medicheck.type.parson.ParsonNameType;
import com.studiojozu.medicheck.type.parson.ParsonPhotoType;

import java.util.ArrayList;
import java.util.Map;

/**
 * 飲む人に関するサービス
 */
public class ParsonService {

    @NonNull
    private final ParsonRepository mParsonRepository;

    public ParsonService() {
        mParsonRepository = new ParsonRepository();
    }

    @NonNull
    public ArrayList<Parson> findAll(@NonNull Context context) {
        ArrayList<Map<ColumnBase, ADbType>> repositoryDataList = mParsonRepository.findAll(context);
        return createParsonList(repositoryDataList);
    }

    @NonNull
    private ArrayList<Parson> createParsonList(@Nullable ArrayList<Map<ColumnBase, ADbType>> repositoryDataList) {
        if (repositoryDataList == null)
            return new ArrayList<>();

        ArrayList<Parson> parsonList = new ArrayList<>(repositoryDataList.size());
        for (Map<ColumnBase, ADbType> repositoryData : repositoryDataList) {
            Parson parson = convertToEntity(repositoryData);
            parsonList.add(parson);
        }

        return parsonList;
    }

    @NonNull
    private Parson convertToEntity(@NonNull Map<ColumnBase, ADbType> repositoryData) {
        ParsonIdType parsonIdType = (ParsonIdType) repositoryData.get(ParsonRepository.COLUMN_ID);
        ParsonNameType parsonNameType = (ParsonNameType) repositoryData.get(ParsonRepository.COLUMN_NAME);
        ParsonPhotoType parsonPhotoType = (ParsonPhotoType) repositoryData.get(ParsonRepository.COLUMN_PHOTO);

        return new Parson(parsonIdType, parsonNameType, parsonPhotoType);
    }
}
