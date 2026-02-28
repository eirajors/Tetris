package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.uib.inf101.grid.CellPosition;

public class Tetromino {
    private static ArrayList<Character> legalSymbols = new ArrayList<Character>(List.of('L', 'J', 'S', 'Z', 'T', 'I', 'O'));
    private char symbol;
    private boolean [][] matrise;
    private CellPosition pos;

    private static final HashMap<Character, boolean[][]> MATRISER = new HashMap<>();

    static{
        MATRISER.put('L', new boolean[][]{
            { false, false, false },
            { true,  true,  true },
            { true, false,  false }});
        MATRISER.put('J', new boolean[][]{
            { false, false, false },
            { true,  true,  true },
            { false, false,  true }});
        MATRISER.put('S', new boolean[][]{
            { false, false, false },
            { false,  true,  true },
            { true, true,  false }});
        MATRISER.put('Z', new boolean[][]{
            { false, false, false },
            { true,  true,  false },
            { false, true,  true }});
        MATRISER.put('T', new boolean[][]{
            { false, false, false },
            { true,  true,  true },
            { false, true,  false }});
        MATRISER.put('I', new boolean[][]{
            { false, false, false },
            { true,  true,  true },
            { false, true,  false }});
        MATRISER.put('O', new boolean[][]{
            { false, false, false, false },
            { false,  true,  true, false },
            { false, true,  true, false },
            { false, false, false, false }});
    }

    //konstruktør
    private Tetromino(char symbol, boolean [][] matrise, CellPosition pos){
        this.symbol = symbol;
        this.matrise = matrise;
        this.pos = pos; 
    }
    
    public static Tetromino newTetromino(char symbol){
        // char symbol = 'T';
        // boolean[][] newMatrise = new boolean[][]{
        //     { false, false, false },
        //     {  true,  true,  true },
        //     { false,  true, false }
        // };
        if(!legalSymbols.contains(symbol)){
            throw new IllegalArgumentException();
        }

        return new Tetromino(symbol, matrise, new CellPosition(0,0));
    }
}
