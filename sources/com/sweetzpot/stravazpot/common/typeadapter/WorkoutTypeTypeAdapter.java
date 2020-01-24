package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.activity.model.WorkoutType;
import java.io.IOException;

public class WorkoutTypeTypeAdapter extends TypeAdapter<WorkoutType> {
    public void write(JsonWriter out, WorkoutType workoutType) throws IOException {
        out.value((long) workoutType.getRawValue());
    }

    public WorkoutType read(JsonReader in) throws IOException {
        WorkoutType[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int input = in.nextInt();
            for (WorkoutType workoutType : WorkoutType.values()) {
                if (workoutType.getRawValue() == input) {
                    return workoutType;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
