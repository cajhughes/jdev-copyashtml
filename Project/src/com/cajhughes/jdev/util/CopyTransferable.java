package com.cajhughes.jdev.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class CopyTransferable implements Transferable {
    static final DataFlavor RTF_FLAVOR = new DataFlavor("text/rtf", "Rich Text Format");
    private String stringData = null;
    private Object rtfData = null;

    public CopyTransferable(final Object data, final DataFlavor flavor) throws UnsupportedFlavorException {
        addDataFlavor(data, flavor);
    }

    @Override
    public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException {
        Object result = null;
        if (DataFlavor.stringFlavor.isMimeTypeEqual(flavor)) {
            result = stringData;
        }
        else if (RTF_FLAVOR.isMimeTypeEqual(flavor)) {
            result = rtfData;
        }
        else {
            throw new UnsupportedFlavorException(flavor);
        }
        return result;
    }

    @Override
    public boolean isDataFlavorSupported(final DataFlavor flavor) {
        boolean result = false;
        DataFlavor[] flavors = getTransferDataFlavors();
        for (int i = 0; i < flavors.length && !result; i++) {
            if (flavors[i].equals(flavor)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] result = new DataFlavor[2];
        int i = 0;
        if (stringData != null) {
            result[i] = DataFlavor.stringFlavor;
            i++;
        }
        if (rtfData != null) {
            result[i] = RTF_FLAVOR;
        }
        return result;
    }

    public void addDataFlavor(final Object data, final DataFlavor flavor) throws UnsupportedFlavorException {
        if ((DataFlavor.stringFlavor.isMimeTypeEqual(flavor)) && (data instanceof String)) {
            stringData = (String)data;
        }
        else if (RTF_FLAVOR.equals(flavor)) {
            rtfData = data;
        }
        else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
