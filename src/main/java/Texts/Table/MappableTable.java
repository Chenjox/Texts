package Texts.Table;

import java.util.List;
import java.util.function.Function;

/**
 * A contract specifying how a mappable Texts.Table should be built.
 * The Map Function is defined as <tt style="color: #FF6347">F: T -> M</tt>
 * @param <T> the type of the content objects
 * @param <M> the type of the object being mappable to
 */
public interface MappableTable<T,M> extends Table<T>{

    /**
     * @return returns the specified mapping function
     */
    Function<T,M> getMapper();

    /**
     * @param column index of the column
     * @param row index of the row
     * @return the mapped content of the cell
     */
    default M getMappedCell(int column, int row){
        return MapCell( column, row, getMapper() );
    };

    /**
     * @param index index of the column
     * @return the mapped content of the column
     */
    default List<M> getMappedColumn(int index){
        return MapColumn( index, getMapper() );
    };

    /**
     * @param index index of the column
     * @return the mapped content of the row
     */
    default List<M> getMappedRow(int index){
        return MapRow(index, getMapper());
    };

    /**
     * Maps a cell with the specified mapper
     * @param column index of the column
     * @param row index of the row
     * @param mapper the map function
     * @param <N> the type being mapped to
     * @return the mapped content of the cell
     */
    default <N> N MapCell(int column, int row, Function<T,N> mapper){
        return mapper.apply( getCell( column, row ) );
    };

    /**
     * Maps a column with the specified mapper
     * @param column index of the column
     * @param mapper the map function
     * @param <N> the type being mapped to
     * @return the mapped column
     */
    <N> List<N> MapColumn(int column, Function<T,N> mapper);

    /**
     * Maps a row with the specified mapper
     * @param row index of the row
     * @param mapper the map function
     * @param <N> the type being mapped to
     * @return the mapped column
     */
    <N> List<N> MapRow(int row, Function<T,N> mapper);
}
