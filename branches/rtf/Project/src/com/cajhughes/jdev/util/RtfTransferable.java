package com.cajhughes.jdev.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class RtfTransferable implements Transferable {
    public static final DataFlavor RTF_FLAVOR = new DataFlavor("text/rtf", "Rich Text Format");
    private Object data = null;

    public RtfTransferable(final Object data) {
        this.data = data;
    }

    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (!RTF_FLAVOR.isMimeTypeEqual(df)) {
            throw new UnsupportedFlavorException(df);
        }
        return data;
    }

    public boolean isDataFlavorSupported(final DataFlavor df) {
        return RTF_FLAVOR.isMimeTypeEqual(df);
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] result = {RTF_FLAVOR};
        return result;
    }
}
