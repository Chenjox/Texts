package Texts;

/**
 * A contract specifying how objects can be put into a rectangular box.
 */
public interface StringBoxable {

    //TODO Docs checken. Überprüfen ob es zu Pauchus Anmerkungen passt
    //Du sollst ja im Interface nicht jedes Detail der Implementierung erklären
    //deswegen ist es ja nur ein interface
    //Was das interface macht, ist, sicherstellen, dass eine funktion vorhanden ist
    //um somit kompatibilität mit anderen funktionen zu garantieren
    //funktion im sinne von eigenschaft
    //nicht im programmiersinn
    //wobei das auch nicht ganz falsch wäre

    /**
     * Gets the Width (the amount of chars) of the Box
     * <pre>
     *     &lt;--- Width ---&gt;
     *     +-------------+
     *     |             |
     *     +-------------+
     * </pre>
     * @return Width of the Box (the amount of chars)
     */
    int getWidth();

    /**
     * Gets the Height (the amount of chars) of the Box
     * <pre>
     *      +-------------+ ^
     *      |             | |
     *      |             |  Height
     *      |             | |
     *      +-------------+ v
     * </pre>
     * @return Height of the Box (the amount of Lines)
     */
    int getHeight();

    /**
     * Returns the Line specified. Useful for rendering Boxes inside Boxes.
     * @param Line the index of the Line, starting at zero for the first Line
     * @return Returns the specified Line of the Box
     */
    String getLine(int Line);

    /**
     *
     * @return Returns the complete String of the Box
     */
    String toBoxString();

}
