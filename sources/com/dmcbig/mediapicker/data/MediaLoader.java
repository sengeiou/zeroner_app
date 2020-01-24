package com.dmcbig.mediapicker.data;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;
import java.util.ArrayList;

public class MediaLoader extends LoaderM implements LoaderCallbacks {
    String[] MEDIA_PROJECTION = {"_data", "_display_name", "date_added", "media_type", "_size", "_id", "parent"};
    Context mContext;
    DataCallback mLoader;

    public MediaLoader(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        Uri queryUri = Files.getContentUri("external");
        return new CursorLoader(this.mContext, queryUri, this.MEDIA_PROJECTION, "media_type=1 OR media_type=3", null, "date_added DESC");
    }

    public void onLoadFinished(Loader loader, Object o) {
        ArrayList<Folder> folders = new ArrayList<>();
        Folder allFolder = new Folder(this.mContext.getResources().getString(R.string.all_dir_name));
        folders.add(allFolder);
        Folder allVideoDir = new Folder(this.mContext.getResources().getString(R.string.video_dir_name));
        folders.add(allVideoDir);
        Cursor cursor = (Cursor) o;
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow("date_added"));
            int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow("media_type"));
            long size = cursor.getLong(cursor.getColumnIndexOrThrow("_size"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            if (size >= 1) {
                String dirName = getParent(path);
                Media media = new Media(path, name, dateTime, mediaType, size, id, dirName);
                allFolder.addMedias(media);
                if (mediaType == 3) {
                    allVideoDir.addMedias(media);
                }
                int index = hasDir(folders, dirName);
                if (index != -1) {
                    ((Folder) folders.get(index)).addMedias(media);
                } else {
                    Folder folder = new Folder(dirName);
                    folder.addMedias(media);
                    folders.add(folder);
                }
            }
        }
        this.mLoader.onData(folders);
        cursor.close();
    }

    public void onLoaderReset(Loader loader) {
    }
}
