package com.example.roma.servertest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roma on 8/2/2016.
 */

public abstract class Piece{

        protected final int _color;//added by jony
        protected Tile _tile;//added by jony
        protected Point _pointPosition;//added by jony
        protected boolean _isActive;//added by jony
        protected boolean _checksKing;
        protected final int TILES_NUMBER_IN_A_ROW = 8;

        protected boolean _isFlipped;
        protected String name;
        protected String color;
        protected int image;
        protected int position;
        protected boolean isEmpty;

       /* //default constructor added by jony
        public Piece(int color, Tile tile) {

            _color = color;
            _tile = tile;
            _isActive = true;
    }*/

    public Piece (String name , String color, int pos){

        this.name = name;
        this.color = color;
        this.position = pos;
        isEmpty = name.equals("empty") ? true : false;
        _pointPosition = new Point();
        _isActive = true;
        if (color.equals("white"))
            _color = Color.WHITE;
        else
            _color = Color.BLACK;
    }
    
    //constructor that copies the piece`s contents
    public Piece (Piece piece)
    {
            
    }

    public boolean getActive()
    {
        return _isActive;
    }

    public void setActive(boolean bool)
    {
        _isActive = bool;
    }

    public boolean checks()
    {
        return _checksKing;
    }

    public void setCheck(boolean bool)
    {
        _checksKing = bool;
    }

    public int getImg(){
        return image;
    }

    public void setEmpty(boolean b){
        isEmpty=b;
    }

    public String getName() {
        return name;
    }

    public String getColor(){
        return color;
    }

    public int getIntColor()
    {
        return _color;
    }

    public Point getPointPosition(){
        return _pointPosition;
    }

    public int getPosition(){
        return position;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public void setPointPosition(int x, int y)
    {
        _pointPosition.set(x, y);
    }

    public void setPointPosition(Point point)
    {
        _pointPosition = point;
    }

    public void setPosition(int pos){
        position=pos;
    }

    // each peace should override this method and return  the legal moves from the piece location according to the games piece layout
    abstract  ArrayList<Integer> getLegalMoves(Game game);

    protected boolean canMove(Game game)
    {
        Piece[] pieces = game.getBoard2();
        /*Piece theKing = null;
        for (Piece p : pieces)
        {
            if (p.getIntColor() == getIntColor() && p instanceof King)
            {
                theKing = p;
            }
        }*/

        //extracting only opponent pieces
        ArrayList<Piece> opponentPieces = new ArrayList<Piece>();
        for (Piece p : pieces)
        {
            if (p.getIntColor() != _color && !(p instanceof Empty))// add piece if not empty and with opponent color
                opponentPieces.add(p);
        }

        setActive(false);//set this piece as inactive (hollow) to see if opponent pieces check the king if it moves

        for (Piece p : opponentPieces)//looping through opponent pieces
        {
            p.getLegalMoves(game);
            if (p.checks())
            {
                setActive(true);
                return false;
            }
            /*ArrayList<Integer> legalMoves = p.getLegalMoves(game);
            for (Integer i : legalMoves)//looping through the pieces legalMoves to see if they check the king
            {
                if (i == theKing.getPosition())//if check then this piece can`t move
                {
                    setActive(true);
                    return false;
                }
            }*/
        }
        setActive(true);
        return true;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("color", color);
        json.put("image", image);
        json.put("position", position);
        return json;
    }
}
