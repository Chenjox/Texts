package Texts.Table.Rendering.AsciiDoc;

import Texts.Table.Advanced.AdvFuncColumn;
import Texts.Table.FunctionalTable;
import Texts.Table.MappableTable;
import Texts.Table.Table;

import java.util.ArrayList;
import java.util.List;

public class AsciiDocTable implements Table<String>{

    private static final String TABLE_BLOCK_DELIMITER = "|===";

    private boolean HasFooter;
    private boolean HasHeader;
    private String title;
    private final ArrayList<ArrayList<AsciidocCell>> cells;

    public <T> AsciiDocTable(Table<T> t){
        ArrayList<ArrayList<AsciidocCell>> d = new ArrayList<>();
        for (int i = 0; i < t.getColumns(); i++) {
            ArrayList<AsciidocCell> col = new ArrayList<>();
            for (int j = 0; j < t.getRows(); j++) {
                col.add( new AsciidocCell( t.getCell( i, j ).toString() ) );
            }
            d.add( col );
        }
        this.cells = d;
    }

    public <T> AsciiDocTable(MappableTable<T, String> t){
        ArrayList<ArrayList<AsciidocCell>> d = new ArrayList<>();
        for (int i = 0; i < t.getColumns(); i++) {
            ArrayList<AsciidocCell> col = new ArrayList<>();
            for (int j = 0; j < t.getRows(); j++) {
                col.add( new AsciidocCell( t.getMappedCell( i, j ) ) );
            }
            d.add( col );
        }
        this.cells = d;
    }

    public <T> AsciiDocTable(FunctionalTable<T> t, String emptyCells){
        ArrayList<ArrayList<AsciidocCell>> d = new ArrayList<>();
        for (int i = 0; i < t.getColumns(); i++) {
            ArrayList<AsciidocCell> col = new ArrayList<>();
            for (int j = 0; j < t.getRows(); j++) {
                col.add( new AsciidocCell( t.getCell( i, j ).toString() ) );
            }
            for (int j = 0; j < t.getFunctionalRows(); j++) {
                col.add( new AsciidocCell( t.getComputedFunctionalRowEntry( i, j ).toString() ) );
            }
            d.add( col );
        }
        for (int i = 0; i < t.getFunctionalColumns(); i++) {
            ArrayList<AsciidocCell> funcCol = new ArrayList<>();
            for (int j = 0; j < t.getRows(); j++) {
                funcCol.add( new AsciidocCell( t.getComputedFunctionalColumnEntry( i, j ).toString() ) );
            }
            for (int j = 0; j < t.getFunctionalRows(); j++) {
                funcCol.add( new AsciidocCell( emptyCells ) );
            }
            d.add( funcCol );
        }
        this.cells = d;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHasFooter(boolean hasFooter) {
        HasFooter = hasFooter;
    }

    public void setHasHeader(boolean hasHeader) {
        HasHeader = hasHeader;
    }

    public void addHeaderRow(List<String> Header){
        this.addRow( 0, Header );
    }

    public void addFooterRow(List<String> Footer){
        this.addRow( getRows()-1, Footer );
    }

    public void setCellFormat(int Column, int Row, char Style, int horizontalAlignment, int VerticalAlignment){
        cells.get( Column ).get( Row ).setCellStyle( Style, horizontalAlignment, VerticalAlignment );
    }

    @Override
    public void setCell(int Column, int Row, String content) {
        cells.get( Column ).get( Row ).setCellContent( content );
    }

    @Override
    public String getCell(int Column, int Row) {
        return cells.get( Column ).get( Row ).getCellContent();
    }

    @Override
    public int getColumns() {
        return cells.size();
    }

    @Override
    public void setColumn(int index, List<String> Column) {
        for (int i = 0; i < getColumns(); i++) {
            cells.get( index ).get( i ).setCellContent( Column.get( i ) );
        }
    }

    @Override
    public List<String> getColumn(int index) {
        ArrayList<String> r = new ArrayList<>();
        for (int i = 0; i < getColumns(); i++) {
            r.add( cells.get( index ).get( i ).getCellContent() );
        }
        return r;
    }

    @Override
    public int getRows() {
        return !cells.isEmpty() ? cells.get( 0 ).size() : 0;
    }

    @Override
    public void setRow(int index, List<String> Row) {
        for (int i = 0; i < getRows(); i++) {
            cells.get( i ).get( index ).setCellContent( Row.get( i ) );
        }
    }

    @Override
    public List<String> getRow(int index) {
        ArrayList<String> r = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            r.add( cells.get( i ).get( index ).getCellContent() );
        }
        return r;
    }

    @Override
    public void addRow(List<String> Row) {
        for (int i = 0; i < getColumns(); i++) {
            cells.get( i ).add( new AsciidocCell( Row.get( i ) ) );
        }
    }

    @Override
    public void addRow(int index, List<String> Row) {
        for (int i = 0; i < getColumns(); i++) {
            cells.get( i ).add( index, new AsciidocCell( Row.get( i ) ) );
        }
    }

    public void addColumn(List<String> Column){
        ArrayList<AsciidocCell> c = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            c.add( new AsciidocCell( Column.get( i ) ) );
        }
        cells.add( c );
    }

    public void addColumn(int index, List<String> Column){
        ArrayList<AsciidocCell> c = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            c.add( new AsciidocCell( Column.get( i ) ) );
        }
        cells.add( index, c );
    }

    public String render(){
        return renderGetTitle() + '\n' +
                renderGetAttributes() + '\n' +
                TABLE_BLOCK_DELIMITER + '\n' +
                getCells() +
                TABLE_BLOCK_DELIMITER + '\n';
    }
    private String getCells(){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                b.append( cells.get( j ).get( i ).getCell() ).append( '\n' );
            }
            b.append( '\n' );
        }
        return b.toString();
    }
    private String renderGetTitle(){
        return title != null ? '.' + title : "";
    }
    private String renderGetAttributes(){
        StringBuilder b = new StringBuilder();
        b.append( '[' );
        if(HasHeader)b.append( "%header" );
        if(HasFooter)b.append( "%footer" );
        if(HasFooter||HasHeader)b.append( ',' );
        b.append( "cols=\"" );
        for (int i = 0; i < getColumns()-1; i++) {
            b.append( "1," ); // 1 can be replaced with columnstyle
        }
        b.append( "1" ); //Last column
        b.append( "\"]" );
        return b.toString();
    }
}
