package com.example.roma.servertest;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Roma on 8/2/2016.
 */

public class King extends Piece {

    //constructor - added by jony
    public  King(Context context, int color, Tile tile){
        super(context, color, tile);
    }

    public King(Context context, String name, String color, int pos) {
        super(context, name, color, pos);
        // TODO Auto-generated constructor stub
        if (color.equals("white")){
            this.image =R.drawable.klt60;
        }
        else
            this.image=R.drawable.kdt60;
    }

    @Override
    ArrayList<Integer> getLegalMoves(Game game) {
        ArrayList<Integer> legalMoves = new ArrayList<Integer>();
        Piece[] pieces = game.getBoard2();

        int temp ;

        // up
        temp =position-8;
        if(temp>=0 && (pieces[temp].isEmpty()  || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // up left
        temp =position-9;
        if(( temp>=0 )&& ( temp%8 != 7 ) && (pieces[temp].isEmpty()  || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // up right
        temp =position-7;
        if(( temp>=0 )&& ( temp%8 != 0 ) && ( (pieces[temp].isEmpty())  || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        //  right
        temp =position+1;
        if(( temp>=0 )&& ( temp%8 != 0 ) && ((pieces[temp].isEmpty())  || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // left
        temp =position-1;
        if(( temp>=0 )&& ( temp%8 != 7 ) && ( (pieces[temp].isEmpty() ) || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // down left
        temp =position+7;
        if(( temp<64 )&& ( temp%8 != 7 ) && (( pieces[temp].isEmpty())  || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // left
        temp =position+9;
        if(( temp<64 )&& ( temp%8 != 0 ) && (( pieces[temp].isEmpty() ) || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);

        // left
        temp =position+8;
        if(( temp<64 )&& ( temp%8 != 7 ) && ( (pieces[temp].isEmpty() ) || !pieces[temp].getColor().equals(pieces[position].getColor())) )
            legalMoves.add(temp);


        return legalMoves;
    }


}

