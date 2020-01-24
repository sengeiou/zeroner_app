package com.dmcbig.mediapicker.data;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Folder;
import java.util.ArrayList;

public class ImageLoader extends LoaderM implements LoaderCallbacks {
    String[] IMAGE_PROJECTION = {"_data", "_display_name", "date_added", "mime_type", "_size", "_id"};
    Context mContext;
    DataCallback mLoader;

    public ImageLoader(Context context, DataCallback loader) {
        this.mContext = context;
        this.mLoader = loader;
    }

    public Loader onCreateLoader(int picker_type, Bundle bundle) {
        return new CursorLoader(this.mContext, Media.EXTERNAL_CONTENT_URI, this.IMAGE_PROJECTION, null, null, "date_added DESC");
    }

    public void onLoadFinished(Loader loader, Object o) {
        ArrayList<Folder> folders = new ArrayList<>();
        Folder allFolder = new Folder(this.mContext.getResources().getString(R.string.all_image));
        folders.add(allFolder);
        Cursor cursor = (Cursor) o;
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow("date_added"));
            int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow("mime_type"));
            long size = cursor.getLong(cursor.getColumnIndexOrThrow("_size"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            if (size >= 1) {
                String dirName = getParent(path);
                com.dmcbig.mediapicker.entity.Media media = new com.dmcbig.mediapicker.entity.Media(path, name, dateTime, mediaType, size, id, dirName);
                allFolder.addMedias(media);
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
