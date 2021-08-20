package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface EditColumnExtensionPoint extends ExtensionPoint {

    void loadEditColumnModifications(Scene scene);
}
