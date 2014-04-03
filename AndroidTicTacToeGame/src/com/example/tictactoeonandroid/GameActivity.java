// 	Josh Bacon
//	Tic Tac Toe App
//	2/12/2014
//	Version: 0.2
//	
//	Description: This Tic Tac Toe android program is NFL themed,
//	The layout I used is the LinearLayout from android, which
//	I nested within each other in order to make the buttons 
//	fit dynamically with different device sizes. 

package com.example.tictactoeonandroid;

import TicTacToeGame.TicTacToe;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	private Button[] boardBtns;
	private Button resetButton;
	private Button settingsButton;
	private Button chooseTeamButton;
	private AlertDialog.Builder dialogBuilder;
	private TextView player1WinsText;
	private TextView tiesText;
	private TextView player2WinsText;
	private TicTacToe game;
	private String winner = "";
	private int player1Wins = 0;
	private int computerWins = 0;
	private int player2Wins = 0;
	private int ties = 0;
	private int playerTeam;
	private int player2Team;
	private MediaPlayer mp;
	
	Bundle extras;
	int numPlayers;
	int player1TeamImgId;
	int player2TeamImgId;
	int difficulty = TicTacToe.DIFFICULTY_EASY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		if(savedInstanceState == null) {
			initialize();
			return;
		}
			//App being reloaded
			Toast.makeText(this,  "App being reloaded with instanceState", Toast.LENGTH_SHORT);
			computerWins = savedInstanceState.getInt("Computer Wins");
			initialize();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void initialize() {
		mp = MediaPlayer.create(this, R.raw.click);
		extras = getIntent().getExtras();
		if(extras == null) {
			numPlayers = 0;
			player1TeamImgId = 0;
			player2TeamImgId = 0;
		}
		else {
			numPlayers = extras.getInt("numPlayers");
			player1TeamImgId = extras.getInt("player1TeamImgId");
			player2TeamImgId = extras.getInt("player2TeamImgId");
			difficulty = extras.getInt("difficulty");
		}
		if(numPlayers == 1) {
			game = new TicTacToe(difficulty);
		}
		else
			game = new TicTacToe();
		boardBtns = new Button[9];
		boardBtns[0] = (Button) findViewById(R.id.button0);
		boardBtns[1] = (Button) findViewById(R.id.button1);
		boardBtns[2] = (Button) findViewById(R.id.button2);
		boardBtns[3] = (Button) findViewById(R.id.button3);
		boardBtns[4] = (Button) findViewById(R.id.button4);
		boardBtns[5] = (Button) findViewById(R.id.button5);
		boardBtns[6] = (Button) findViewById(R.id.button6);
		boardBtns[7] = (Button) findViewById(R.id.button7);
		boardBtns[8] = (Button) findViewById(R.id.button8);
		resetButton = (Button) findViewById(R.id.reset);
		settingsButton = (Button) findViewById(R.id.settings);
		chooseTeamButton = (Button) findViewById(R.id.chooseTeam);
		
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Game Over");
		dialogBuilder.setCancelable(false);
		dialogBuilder.setTitle(winner);
		dialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(numPlayers == 1)
					game = new TicTacToe(difficulty);
				else
					game = new TicTacToe();
				for(int i = 0; i < 9; i++) {
					boardBtns[i].setEnabled(true);
					boardBtns[i].setBackgroundResource(R.drawable.blank);
				}
				dialog.cancel();
			}
		});
		
		player1WinsText = (TextView) findViewById(R.id.playerWins);
		tiesText = (TextView) findViewById(R.id.textView);
		player2WinsText = (TextView) findViewById(R.id.computerWins);
		
		//Click listener that fires if buttons are pressed
		//Button Images (NFL Themed) are changed, and buttons disabled,
		//Toast and Dialog are also used
		for(int i = 0; i < 9; i++) {
			final int j = i; 
			boardBtns[j].setOnClickListener(new OnClickListener());
		}
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mp.start();
				if(numPlayers == 1)
					game = new TicTacToe(difficulty);
				else
					game = new TicTacToe();
				for(int i = 0; i < 9; i++) {
					boardBtns[i].setEnabled(true);
					boardBtns[i].setBackgroundResource(R.drawable.blank);
				}
			}
		});
		chooseTeamButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Choose Team Button\nstill in progress", Toast.LENGTH_LONG).show();
			}
		});
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Settings Button\nstill in progress", Toast.LENGTH_LONG).show();
			}
		});
	}
	private class OnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			mp.start();
			int j;
			switch(v.getId()) {
				case R.id.button0: j = 0;
					break;
				case R.id.button1: j = 1;
					break;
				case R.id.button2: j = 2;
					break;
				case R.id.button3: j = 3;
					break;
				case R.id.button4: j = 4;
					break;
				case R.id.button5: j = 5;
					break;
				case R.id.button6: j = 6;
					break;
				case R.id.button7: j = 7;
					break;
				case R.id.button8: j = 8;
					break;
				default: j = -1;
					break;
			}
			doBoardClick(j);
		}
	}
	
	private void doBoardClick(int j) {
		Log.d("TTT", "Pressed Button: "+j);
		//Disable Button after selecting
		boardBtns[j].setEnabled(false);
		//If game not over, input 
		if(!game.isGameOver()) {
			if(game.getBoardSpot(j) == ' ') {
				//Player vs Player scenrio
				if(numPlayers == 2) {
					if(game.getWhoWentLast() == 'O') {
						boardBtns[j].setBackgroundResource(player1TeamImgId);
					}
					else {
						boardBtns[j].setBackgroundResource(player2TeamImgId);
					}
					game.inputMove(j);
				}
				//Player vs Computer Scenrio
				else {
					game.inputMove(j);
					boardBtns[j].setBackgroundResource(player1TeamImgId);
					boardBtns[game.getLastComputerMove()].setBackgroundResource(player2TeamImgId);
					boardBtns[game.getLastComputerMove()].setEnabled(false);
					Toast.makeText(getApplicationContext(), "Computer Moved!", Toast.LENGTH_SHORT).show();
				}
				//If game is now over
				//get winner and add to computerWins/playerWins/or ties
				//Change display text
				if(game.isGameOver()) {
					//Disable all buttons
					for(int k = 0; k < 9; k ++) {
						boardBtns[j].setEnabled(false);
					}
					if(game.getWinner() == 'O') {
						if(numPlayers == 1) {
							winner = "Computer Won!";
							computerWins++;
						}
						else {
							winner = "Player 2 Won!";
							player2Wins++;
						}
					}
					else if(game.getWinner() == 'X'){
						winner = "Player 1 Won!";
						player1Wins++;
					}
					else {
						winner = "Tie!";
						ties++;
					}
					player1WinsText.setText("Player 1:\n"+player1Wins);
					tiesText.setText("Ties:\n"+ties);
					if(numPlayers == 1)
						player2WinsText.setText("Computer:\n"+computerWins);
					else
						player2WinsText.setText("Player 2:\n"+player2Wins);
					dialogBuilder.setMessage(winner);
					AlertDialog alertDialog = dialogBuilder.create();
					alertDialog.show();
				}
			}
		}
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		computerWins = savedInstanceState.getInt("ComputerWins");
	}
}
