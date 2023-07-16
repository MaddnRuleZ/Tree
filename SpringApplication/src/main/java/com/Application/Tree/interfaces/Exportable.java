package com.Application.Tree.interfaces;

import java.util.List;

public interface Exportable {
    /**
     * get the recompiled source code of the Latex Doc
     *
     * @return
     */
    List<String> toText();
}
