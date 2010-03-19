package com.cajhughes.jdev.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class RtfTransferable implements Transferable {
    public static final DataFlavor PLAIN_FLAVOR = new DataFlavor("text/plain", "Plain Text");
    public static final DataFlavor RTF_FLAVOR = new DataFlavor("text/rtf", "Rich Text Format");
    private Object plainData = null;
    private Object rtfData = null;

    public RtfTransferable(final Object plainData, final Object rtfData) {
        this.plainData = plainData;
        this.rtfData = rtfData;
    }

    @Override
    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
        Object result = null;
        if (PLAIN_FLAVOR.isMimeTypeEqual(df)) {
            result = plainData;
        }
        else if (RTF_FLAVOR.isMimeTypeEqual(df)) {
            result = rtfData;
        }
        else {
            throw new UnsupportedFlavorException(df);
        }
        return result;
    }

    @Override
    public boolean isDataFlavorSupported(final DataFlavor df) {
        return (PLAIN_FLAVOR.isMimeTypeEqual(df) || RTF_FLAVOR.isMimeTypeEqual(df));
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] result = { PLAIN_FLAVOR, RTF_FLAVOR };
        return result;
    }
}
