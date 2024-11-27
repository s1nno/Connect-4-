package com.example.connectfour;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        // Initialize the GridLayout
        mGrid = parentView.findViewById(R.id.board_grid);

        // Set click listeners for all buttons in the grid
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        // Initialize the ConnectFourGame object
        mGame = new ConnectFourGame();

        // Start a new game or restore the previous state
        if (savedInstanceState == null) {
            startGame();  // Start a new game
        } else {
            // Restore the game state
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setDisc();  // Update the UI with the restored game state
        }

        return parentView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current game state
        outState.putString(GAME_STATE, mGame.getState());
    }

    // Start a new game and update the UI
    private void startGame() {
        mGame.newGame();
        setDisc();
    }

    // Handle button clicks on the grid
    private void onButtonClick(View view) {
        // Determine the button's position in the grid
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.COL;
        int col = buttonIndex % ConnectFourGame.COL;

        // Select a disc and update the game state
        mGame.selectDisc(row, col);
        setDisc();  // Update the UI

        // Check if the game is over
        if (mGame.isGameOver()) {
            // Display a message if the game is won
            if (mGame.isWin()) {
                Toast.makeText(this.requireActivity(), "Congratulations " + mGame.getPlayer(), Toast.LENGTH_SHORT).show();
            }

            // Reset the game
            mGame.newGame();
            setDisc();  // Refresh the board
        }
    }

    // Update the UI to reflect the current state of the board
    private void setDisc() {
        int childCount = mGrid.getChildCount();

        for (int buttonIndex = 0; buttonIndex < childCount; buttonIndex++) {
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Determine the button's position in the grid
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            // Create drawable objects for the discs
            Drawable whiteDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_white);
            Drawable redDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_red);
            Drawable blueDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue);

            // Wrap drawables for compatibility
            whiteDisc = DrawableCompat.wrap(whiteDisc);
            redDisc = DrawableCompat.wrap(redDisc);
            blueDisc = DrawableCompat.wrap(blueDisc);

            // Set the button's background based on the disc color
            int discColor = mGame.getDisc(row, col);

            if (discColor == ConnectFourGame.BLUE) {
                gridButton.setBackground(blueDisc);
            } else if (discColor == ConnectFourGame.RED) {
                gridButton.setBackground(redDisc);
            } else if (discColor == ConnectFourGame.EMPTY) {
                gridButton.setBackground(whiteDisc);
            }
        }
    }
}
