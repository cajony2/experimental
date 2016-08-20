package com.example.roma.servertest;

/**
 * Created by Roma & Jony on 8/10/2016.
 */
import android.util.Log;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Game {

    //variables
    private Piece[] tiles;
    private String player1;
    private String player2;
    private int status;
    private String turn;

    //constructor
    public Game (String _player1){
        player1=_player1;
        player2="";
        status = 0 ;      // 0=  create new game;
        turn = player1;
        createBoard();
    }

    //receiving json from server and create new game object
    public Game (JSONObject gameJson){
        Log.d("ingameConstructor","creating new game from, json");
        try {
            player1 = gameJson.getString("player1");
            Log.d("name:",player1);
            player2 = null;
            status = gameJson.getInt("status");
            turn = gameJson.getString("turn");
            tiles = new Piece[64];
            JSONArray piecesJson = gameJson.getJSONArray("pieces");
            getPiecesFromJson(piecesJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createBoard(){
        tiles =  new Piece[64];

        //insert the starting pieces
        // init the one dim array  fill in the black and white tiles
        for(int i=0 ; i<tiles.length ; i++){
            if(i%16 < 8){
                if(i%2 != 0)
                    tiles[i]=new Empty("empty" ,"black",i);
                else
                    tiles[i]=new Empty("empty" ,"white",i);
            }else
            if(i%2 != 0)
                tiles[i]=new Empty("empty" ,"white",i);
            else
                tiles[i]=new Empty("empty" ,"black",i);
        }

        // add the pawns
        for(int i =8 ; i<16 ;i++){
            tiles[i] = new Pawn("pawn","white",i);
            tiles[i+40]= new Pawn("pawn","black",i+40);
        }

        // add white pieces
        tiles[56]=(new Rook("rook" , "white",56));
        tiles[57]=(new Knight("Knight" , "white",57));
        tiles[58]=(new Bishop("Bishop" , "white",58));
        tiles[59]=(new Queen("queen" , "white",59));
        tiles[60]=(new King("king" , "white",60));
        tiles[61]=(new Bishop("Bishop" , "white",61));
        tiles[62]=(new Knight("Knight" , "white",62));
        tiles[63]=(new Rook("rook" ,"white",63));

        // add black pieces
        tiles[0]=(new Rook("rook" ,"black",0));
        tiles[1]=(new Knight("Knight" , "black",1));
        tiles[2]=(new Bishop("Bishop" , "black",2));
        tiles[3]=(new Queen("queen" , "black",3));
        tiles[4]=(new King("king" , "black",4));
        tiles[5]=(new Bishop("Bishop" , "black",5));
        tiles[6]=(new Knight("Knight" , "black",6));
        tiles[7]=(new Rook("rook" , "black",7));
    }


    public String getPlayer1(){
        return player1;
    }
    public String getPlayer2(){
        return player2;
    }
    public int getStatus(){
        return status;
    }
    public String getTurn(){
        return turn;
    }

    public Piece getPiece(int pos){
        if(pos<64)
            return tiles[pos];
        else {
            Log.d("getPiece", "no piece at " + pos);
            return null;
        }
    }

    public Piece[] getBoard2(){
        return tiles;
    }

    private void getPiecesFromJson(JSONArray array) throws JSONException {
        int size = array.length();
        Log.d("numberOfPieces"," "+size);
        JSONObject temp ;
        for(int i=0 ; i<size ;i++) {
            temp = array.getJSONObject(i);
            String piece = temp.getString("name");
            String color = temp.getString("color");
            int position = temp.getInt("position");

            switch (piece){
                case "empty":
                    tiles[i] = new Empty(piece, color, position);
                    break;
                case "bishop":
                    tiles[i] = new Bishop(piece, color, position);
                    break;
                case "king":
                    tiles[i] = new King(piece, color, position);
                    break;
                case "queen":
                    tiles[i] = new Queen(piece, color, position);
                    break;
                case "knight":
                    tiles[i] = new Knight(piece, color, position);
                    break;
                case "pawn":
                    tiles[i] = new Pawn(piece, color, position);
                    break;
                case "rook":
                    tiles[i] = new Rook(piece, color, position);
                    break;
                default:
                    Log.d("error","in default: "+piece);
            }
        }
    }

    public JSONObject toJson()  {

        JSONObject json = new JSONObject();
        try {
            json.put("player1", player1);
            json.put("player2", player2);
            json.put("status", status);
            json.put("turn", turn);
            json.put("pieces", getPiecesJson());
        } catch (JSONException e) {
            Log.d("chess","fucking error creatinf json player name="+player1);
            e.printStackTrace();
        }
        return json;
    }

    public JSONArray getPiecesJson() throws JSONException{
        JSONArray pieces = new JSONArray();

        for(int i=0; i<tiles.length ;i++)
            pieces.put(tiles[i].toJson());

        return pieces;
    }
}
