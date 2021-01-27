package Texts.Table.Advanced;

import java.util.LinkedList;
import java.util.List;

public class AdvRow<T> {
    private LinkedList<T> cells;

    public AdvRow(List<T> pcells){
        cells = new LinkedList<>();
        cells.addAll( pcells );
    }

    public T getCell(int index){
        return cells.get( index );
    }

    public void setCell(int index, T content){
        cells.set( index,content );
    }

    public List<T> getRow(){
        return cells;
    }

    public void setRow(List<T> row){
        for (int i = 0; i < cells.size(); i++) {
            cells.set(i, row.get( i ));
        }
    }
}
