package com.hfad.notesapp.utilities;

import com.hfad.notesapp.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {

    private static final String SAMPLE_TEXT_1 = "Одна нотатка";
    private static final String SAMPLE_TEXT_2 = "Одна нотатка з\n з розділенням";
    private static final String SAMPLE_TEXT_3 = "При первом знакомстве с классом AsyncTask мы упомянули о том, что он определяется тремя обобщенными параметрами: Params, Progress и Results." +
            "\nОни определяются типами параметров, используемых методами doInBackground(), onProgressUpdate() и onPostExecute().";

    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.SECOND, diff);
        return  cal.getTime();
    }

    public static List<NoteEntity> getNotesList(){
        List<NoteEntity> notesList = new ArrayList<>();
        notesList.add(new NoteEntity(getDate(0), SAMPLE_TEXT_1));
        notesList.add(new NoteEntity(getDate(-1), SAMPLE_TEXT_2));
        notesList.add(new NoteEntity(getDate(-2), SAMPLE_TEXT_3));
        return notesList;
    }
}
