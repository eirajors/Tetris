package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class Tetromino implements Iterable<GridCell>{
    private static ArrayList<Character> legalSymbols = new ArrayList<Character>(List.of('L', 'J', 'S', 'Z', 'T', 'I', 'O'));
    private char symbol;
    private boolean [][] matrise;
    public CellPosition pos;

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

        if(!legalSymbols.contains(symbol)){
            throw new IllegalArgumentException();
        }
        boolean [][] newMatrise = MATRISER.get(symbol);
        
        return new Tetromino(symbol, newMatrise, new CellPosition(0,0));
    }

    public Tetromino shiftedBy(int deltaRow, int deltaCol){
        int newRow = pos.row() + deltaRow;
        int newCol = pos.col() + deltaCol;

        return new Tetromino(symbol, matrise, new CellPosition(newRow, newCol));
    }

    public Tetromino shiftedToTopCenterOf(GridDimension grid){
        int centerCol = (grid.cols() / 2) - (matrise[0].length / 2) ;

        return new Tetromino(symbol, matrise, new CellPosition(0, centerCol));
    }

    @Override
    public Iterator<GridCell> iterator() {

        ArrayList<GridCell> delAvBrikken = new ArrayList<>();
        //for-løkke for å finne de posisjonene som er en del av brikken
        for(int r = 0; r < matrise.length; r++){
            for(int c = 0; c < matrise[r].length; c ++){
                if(matrise[r][c]== true){
                        int row = pos.row() + r;
                        int col = pos.col() + c;
                    delAvBrikken.add(new GridCell(new CellPosition(row, col), symbol));
                }
            }
        }

        return delAvBrikken.iterator();
    }

    @Override
    public boolean equals(Object obj){
        
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false; //stackOverflow, link under
        
        Tetromino tetrObject = (Tetromino) obj;

        return (Arrays.deepEquals(tetrObject.matrise, this.matrise) 
            && (tetrObject.symbol == this.symbol))&& (tetrObject.pos).equals(this.pos);
    }
    //kilde:
    //https://medium.com/@niketl16/equals-and-hashcode-in-java-understanding-overriding-and-best-practices-7775c8fc279c
    @Override
    public int hashCode(){
        return Objects.hash(symbol, Arrays.deepHashCode(matrise), pos); 
    }

    public Tetromino rotatedTetromino(Tetromino tetromino){
        boolean [][] rotatedTet;

        return tetromino;
    }
    
}
