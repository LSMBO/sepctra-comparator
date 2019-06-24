/*
 * 
 */
package fr.lsmbo.msda.spectra.comp.view.dialog;

import fr.lsmbo.msda.spectra.comp.IconResource;
import fr.lsmbo.msda.spectra.comp.IconResource.ICON;
import fr.lsmbo.msda.spectra.comp.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Creates and displays about Spectra-comp software dialog.
 * 
 * @author Aromdhani
 *
 */
public class AboutDialog extends Dialog<String> {
	String name = null;
	String description = null;
	String version = null;

	public AboutDialog() {

		name = Session.SPECTRACOMP_RELEASE_NAME;
		description = Session.SPECTRACOMP_RELEASE_DESCRIPTION;
		version = Session.SPECTRACOMP_RELEASE_VERSION;
		assert name != null : "Software name must not be null or empty";
		assert description != null : "Software description must not be null or empty";
		assert version != null : "Software version must not be null or empty";
		// Create about recover software dialog components
		StringBuilder softwareProperties = new StringBuilder();
		softwareProperties.append("\n").append("##name: ").append(name).append("\n").append("##description: ")
				.append(description).append("\n").append("##version: ").append(version);
		StringBuilder aboutStr = new StringBuilder();
		aboutStr.append("This software is developped by:").append("\n").append("- Aymen ROMDHANI").append("\n")
				.append("- Alexandre BUREL").append("\n").append("Under the supervision of:").append("\n")
				.append("- Christine CARAPITO").append("\n").append("At the laboratory:").append("\n")
				.append("LSMBO (Strasbourg) :UMR 7178 (CNRS/UdS)");
		Label label = new Label(softwareProperties.toString());
		TextArea textArea = new TextArea(aboutStr.toString());

		textArea.setEditable(false);

		// Layout
		GridPane aboutPane = new GridPane();
		aboutPane.setAlignment(Pos.CENTER);
		aboutPane.setPadding(new Insets(10));
		aboutPane.setHgap(25);
		aboutPane.setVgap(25);
		aboutPane.add(label, 0, 0, 5, 5);
		aboutPane.add(textArea, 0, 1, 5, 5);

		/********************
		 * Main dialog pane *
		 ********************/
		// Create and display the main dialog pane
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(aboutPane);
		dialogPane.setHeaderText("Spectra-comp software");
		dialogPane.setGraphic(new ImageView(IconResource.getImage(ICON.SPECTRA_COMP)));
		dialogPane.setPrefSize(600, 400);
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new ImageView(IconResource.getImage(ICON.INFORMATION)).getImage());
		dialogPane.getButtonTypes().addAll(ButtonType.CLOSE);
		Button buttonOk = (Button) dialogPane.lookupButton(ButtonType.CLOSE);
		this.setTitle("About");
		this.setDialogPane(dialogPane);
		// On close dialog
		this.setResultConverter(buttonType -> {
			if (buttonType == ButtonType.CLOSE) {
				return softwareProperties.toString();
			} else {
				return null;
			}
		});
	}

}