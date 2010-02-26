package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.view.resource.CopyResourceUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oracle.ide.controller.IdeAction;
import oracle.ide.keyboard.KeyStrokeContext;
import oracle.ide.keyboard.KeyStrokeMap;
import oracle.ide.keyboard.KeyStrokes;

public final class CopyKeyStrokeContext implements KeyStrokeContext {
    private final Set<IdeAction> actions = new HashSet<IdeAction>();
    private final KeyStrokeMap keyStrokeMap = new KeyStrokeMap();

    public void add(final IdeAction action, final KeyStrokes keyStrokes) {
        actions.add(action);
        keyStrokeMap.put(keyStrokes, action.getCommandId());
    }

    public String getName() {
        return CopyResourceUtil.getString("EXTENSION_NAME");
    }

    public Set getAllActions(final boolean global) {
        if (global) {
            return actions;
        }
        else {
            return Collections.emptySet();
        }
    }

    public KeyStrokeMap getPresetKeyStrokeMap(final Object object, final boolean global) {
        if (global) {
            return keyStrokeMap;
        }
        else {
            return null;
        }
    }

    public List getAllPresets() {
        return Collections.emptyList();
    }

    public String getAcceleratorFile() {
        return null;
    }
}
