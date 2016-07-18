//package com.example.manolis.googlemapspractice;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.format.DateFormat;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.example.admin.turnuptest.TurnUpClasses.Game;
//import com.example.admin.turnuptest.TurnUpClasses.Utils;
//import com.example.admin.turnuptest.TurnUpClasses.enums;
//import com.example.admin.turnuptest.data.CloudDataHandler;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
///**
// * Created by manio on 2016-07-16.
// */
//
//
//    public class temp extends Activity {
//
//        private String name;
//
//        private int maxNumberOfPlayers = -1;
//
//        private int year = -1;
//        private int month = -1;
//        private int day = -1;
//
//        private int hourOfDay = -1;
//        private int minute = -1;
//
//        private String comment;
//        private int selectedSport = -1;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_create_game);
//
//            addItemsToSportSpinner();
//            addOnClickListenerToSpinner();
//
//        }
//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.menu_create_game, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }
//
//        public void addItemsToSportSpinner(){
//
//            Spinner sportSpinner = (Spinner) findViewById(R.id.sportSpinner);
//
//            ArrayAdapter sportSpinnerAdapter = new ArrayAdapter<enums.sport>(this,android.R.layout.simple_list_item_1, enums.sport.values());
//
//            sportSpinner.setAdapter(sportSpinnerAdapter);
//
//
//        }
//
//        public void addOnClickListenerToSpinner(){
//
//            Spinner sportSpinner = (Spinner) findViewById(R.id.sportSpinner);
//
//            sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    selectedSport = position;
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
////TODO deal with later
//                }
//            });
//        }
//
//        public void showTimePickerDialog(View v) {
//            DialogFragment newFragment = new TimePickerFragment();
//            newFragment.show(getFragmentManager(), "timePicker");
//        }
//
//        public static class TimePickerFragment extends DialogFragment
//                implements TimePickerDialog.OnTimeSetListener {
//
//            @Override
//            public Dialog onCreateDialog(Bundle savedInstanceState) {
//                // Use the current time as the default values for the picker
//                final Calendar c = Calendar.getInstance();
//                int hour = c.get(Calendar.HOUR_OF_DAY);
//                int minute = c.get(Calendar.MINUTE);
//
//                // Create a new instance of TimePickerDialog and return it
//                return new TimePickerDialog(getActivity(), this, hour, minute,
//                        DateFormat.is24HourFormat(getActivity()));
//            }
//
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                ((CreateGame) this.getActivity()).hourOfDay = hourOfDay;
//                ((CreateGame) this.getActivity()).minute = minute;
//
//
//            }
//        }
//
//        public static class DatePickerFragment extends DialogFragment
//                implements DatePickerDialog.OnDateSetListener {
//
//            @Override
//            public Dialog onCreateDialog(Bundle savedInstanceState) {
//                // Use the current date as the default date in the picker
//                final Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//
//                // Create a new instance of DatePickerDialog and return it
//                return new DatePickerDialog(getActivity(), this, year, month, day);
//            }
//
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                ((CreateGame) this.getActivity()).year = year;
//                ((CreateGame) this.getActivity()).month = month;
//                ((CreateGame) this.getActivity()).day = day;
//
//            }
//        }
//
//        public void showDatePickerDialog(View v) {
//            DialogFragment newFragment = new DatePickerFragment();
//            newFragment.show(getFragmentManager(), "datePicker");
//        }
//
//        public void createGame(View v) {
//
//            Game game = new Game();
//
//            EditText gameNameEditText = (EditText) findViewById(R.id.gameNameEditText);
//            EditText maxNumberOfPlayersEditText = (EditText) findViewById(R.id.maxPlayers);
//            EditText commentEditText = (EditText) findViewById(R.id.commentEditText);
//
//            boolean isNumber = Utils.isInteger(maxNumberOfPlayersEditText.getText().toString());
//
//            this.comment = commentEditText.getText().toString();
//            this.name = gameNameEditText.getText().toString();
//
//            if (isNumber) {
//                int numberOfPlayers = Integer.parseInt(maxNumberOfPlayersEditText.getText().toString());
//
//                if (numberOfPlayers > 0) {
//
//                    if ((gameNameEditText.getText().toString() != null) && (!gameNameEditText.getText().toString().isEmpty())) {
//
//                        if (!(this.year == -1)) {
//
//                            if (!(this.hourOfDay == -1)) {
//
//                                if(!(this.selectedSport == -1)) {
//
//                                    game.setYear(this.year);
//                                    game.setMonth(this.month);
//                                    game.setDay(this.day);
//
//                                    game.setHourOfDay(this.hourOfDay);
//                                    game.setMinute(this.minute);
//
//                                    game.setSport(this.selectedSport);
//                                    game.setNumberOfPlayers(numberOfPlayers);
//                                    game.setComment(this.comment);
//
//                                    game.setName(this.name);
//
//                                } else{
//                                    Toast toast = Toast.makeText(this, "No Sport Selected", Toast.LENGTH_SHORT);
//                                    toast.show();
//                                    return;
//
//                                }
//
//                            } else {
//
//                                Toast toast = Toast.makeText(this, "No Time Specified", Toast.LENGTH_SHORT);
//                                toast.show();
//                                return;
//                            }
//
//                        } else {
//
//                            Toast toast = Toast.makeText(this, "No Date Specified", Toast.LENGTH_SHORT);
//                            toast.show();
//                            return;
//                        }
//
//
//                    } else {
//                        Toast toast = Toast.makeText(this, "No Game Name Specified", Toast.LENGTH_SHORT);
//                        toast.show();
//                        return;
//                    }
//                } else {
//                    Toast toast = Toast.makeText(this, "Negative Number Of Players", Toast.LENGTH_SHORT);
//                    toast.show();
//                    return;
//
//                }
//
//            } else {
//                Toast toast = Toast.makeText(this, "Invalid Number Of Players", Toast.LENGTH_SHORT);
//                toast.show();
//                return;
//            }
//            CloudDataHandler.createGame(game);
//
//            Intent intent = new Intent(CreateGame.this, Profile.class);
//            startActivity(intent);
//            finish();
//        }
//
//        public void goBackPressed(View v){
//
//            Intent intent = new Intent(CreateGame.this, Profile.class);
//            startActivity(intent);
//            finish();
//        }
//
//    }
//
//
//
//
