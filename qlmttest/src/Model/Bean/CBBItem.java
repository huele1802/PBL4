/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Bean;

/**
 *
 * @author Windows
 */
public class CBBItem {
    private String Value;
    private String Text;

    public CBBItem(String Value, String Text) {
        this.Value = Value;
        this.Text = Text;
    }

    public String getValue() {
        return Value;
    }
    
    public String getText() {
        return Text;
    }
    
    @Override
    public String toString() {
        return Text;
    }
}
