package Texts.Table.Rendering.AsciiDoc;

public class AsciidocCell {

    //Cell formatting: factor span-or-duplication-operator horizontal-alignment-operator vertical-alignment-operator style-operator | cell’s content

    /**
     * 0 for standard, 1 for left alignment, 2 for right alignment, 3 for center alignment
     */
    private int HorizontalAlignment;
    public static final int A_STANDARD = 0;
    public static final int H_LEFT_ALIGNMENT = 1;
    public static final int H_RIGHT_ALIGNMENT = 2;
    public static final int H_CENTER_ALIGNMENT = 3;
    /**
     * 0 for standard, 1 for top alignment, 2 for bottom alignment, 3 for center alignment
     */
    private int VerticalAlignment;
    public static final int V_LEFT_ALIGNMENT = 1;
    public static final int V_RIGHT_ALIGNMENT = 2;
    public static final int V_CENTER_ALIGNMENT = 3;
    /**
     * 'a' for asciidoc content, 'd' for default, 'e' for emphasis, 'h' for header, 'l' for literal, 'm' for monospace, 's' for strong
     */
    private char CellStyle;
    public static final char STANDARD = ' ';
    public static final char DEFAULT = 'd';
    public static final char ASCIIDOC = 'a';
    public static final char EMPHASIS = 'e';
    public static final char HEADER = 'h';
    public static final char LITERAL = 'L';
    public static final char MONOSPACE = 'm';
    public static final char STRONG = 's';

    private String CellContent;

    public AsciidocCell(String content, int horizontalAlignment, int verticalAlignment, char cellStyle){
        this.CellContent = content;
        this.HorizontalAlignment = horizontalAlignment;
        this.VerticalAlignment = verticalAlignment;
        this.CellStyle = cellStyle;
    }
    public AsciidocCell(String content){
        this.CellContent = content;
        this.HorizontalAlignment = A_STANDARD;
        this.VerticalAlignment = A_STANDARD;
        this.CellStyle = STANDARD;
    }
    private String getHorizontalAlignment() {
        switch (HorizontalAlignment){
            case 0: return "";
            case 1: return "<";
            case 2: return ">";
            case 3: return "^";
            default: return "";
        }
    }
    private String getVerticalAlignment(){
        switch (VerticalAlignment){
            case 0: return "";
            case 1: return ".<";
            case 2: return ".>";
            case 3: return ".^";
            default: return "";
        }
    }
    private String getCellStyle(){
        return CellStyle != ' ' ? String.valueOf( CellStyle ) : "";
    }
    public void setCellStyle(char Style, int horizontalAlignment, int verticalAlignment){
        this.CellStyle = Style;
        this.HorizontalAlignment = horizontalAlignment;
        this.VerticalAlignment = verticalAlignment;
    }
    public void setCellStyle(char Style){
        this.CellStyle = Style;
    }
    public String getCell(){
        return getHorizontalAlignment() +
                getVerticalAlignment() +
                getCellStyle() +
                '|' +
                ' ' +
                CellContent;
    }

    public void setCellContent(String cellContent) {
        CellContent = cellContent;
    }
    public String getCellContent() {
        return CellContent;
    }
}
