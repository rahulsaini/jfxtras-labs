package jfxtras.labs.internal.scene.control.skin.edittable.triple;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
import jfxtras.labs.scene.control.edittable.triple.Triple;
import jfxtras.labs.scene.control.edittable.triple.TripleConverter;
import jfxtras.labs.scene.control.edittable.triple.TripleEditTable;

public class TripleEditTableSkinStringStringBoolean<T> extends TripleEditTableSkinBase<T,String,String,Boolean>
{
	// CONSTRUCTOR
	public TripleEditTableSkinStringStringBoolean(
			Predicate<String> validateValue,
			List<Triple<String,String,Boolean>> initialTripleList,
			TripleConverter<T,String,String,Boolean> converter,
			ListChangeListener<Triple<String,String,Boolean>> synchBeanItemTripleChangeLister,
			String[] alertTexts,
			String[] nameOptions,
			ResourceBundle resources,
			TripleEditTable<T,String,String,Boolean> control
			)
	{
		super(validateValue,
				initialTripleList,
				converter,
				synchBeanItemTripleChangeLister,
				alertTexts,
				nameOptions,
				resources,
				control);
	}

	@Override
	protected void setupListeners(List<Triple<String,String,Boolean>> initialTripleList)
	{
		super.setupListeners(initialTripleList);
	    hbox.dataColumn.setCellFactory(column -> new EditCell3<Triple<String,String,Boolean>, String>(emptyString));
	    hbox.primaryColumn.setCellFactory(CheckBoxTableCell.forTableColumn(hbox.primaryColumn));
	}
	
    private class EditCell3<T, E> extends TableCell<T, String>
    {

    	private TextField textField;
    	private String emptyCell;

    	public EditCell3() {
    	    super();
    	    this.emptyCell = null;
    	}
    	public EditCell3(String emptyCell) {
    	    super();
    	    this.emptyCell = emptyCell;
    	}

    	@Override
    	public void startEdit() {
    	    if (!isEmpty()) {
    	        super.startEdit();
    	        createTextField();
    	        setText(null);
    	        setGraphic(textField);
    	        textField.requestFocus();   // select all text
    	        // if not empty unselect all text and move caret to front (can't figure out how to move it to end)
    	        if (! getString().equals(emptyCell)) textField.selectEnd();
    	    }
    	}

    	@Override
    	public void cancelEdit() {
    	    super.cancelEdit();
    	    
    	    setText(getItem());
    	    setGraphic(null);
    	}

    	@Override
    	public void updateItem(String item, boolean empty) {
    	super.updateItem(item, empty);

    	if (empty) {
    	    setText(null);
    	    setGraphic(null);
    	    } else {
    	        if (isEditing()) {
    	            if (textField != null) {
    	            textField.setText(getString());
    	            }
    	            setText(null);
    	            setGraphic(textField);
    	        } else {
    	            setText(getString());
    	            setGraphic(null);
    	        }
    	    }
    	}

    	private void createTextField() {
    	    textField = new TextField(getString());
    	    textField.setOnAction(evt -> {  // enable ENTER commit
    	        commitEdit(textField.getText());
    	    });

    	    textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
    	    
    	    ChangeListener<? super Boolean> changeListener = (observable, oldSelection, newSelection) ->
    	    {
    	        if (! newSelection)
    	            commitEdit(textField.getText());
    	    };
    	    textField.focusedProperty().addListener(changeListener);
    	    
    	    textField.setOnKeyPressed((ke) -> {
    	        if (ke.getCode().equals(KeyCode.ESCAPE)) {
    	            textField.focusedProperty().removeListener(changeListener);
    	            cancelEdit();
    	        }
    	    });
    	}

    	private String getString() {
    	    return getItem() == null ? "" : getItem().toString();
    	}


    	@Override
    	public void commitEdit(String item) {

    	if (isEditing()) {
    	    super.commitEdit(item);
    	} else {
    	    final TableView table = getTableView();
    	    if (table != null) {
    	        TablePosition position = new TablePosition(getTableView(), getTableRow().getIndex(), getTableColumn());
    	        CellEditEvent editEvent = new CellEditEvent(table, position, TableColumn.editCommitEvent(), item);
    	        Event.fireEvent(getTableColumn(), editEvent);
    	    }
    	        updateItem(item, false);
    	        if (table != null) {
    	            hbox.table.edit(-1, null);
    	        }

    	    }
    	}
    }

}
