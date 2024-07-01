package com.skilkihodin.jhauzzer.misc.printers;

import com.skilkihodin.jhauzzer.misc.CatFact;

public final class CatFactPrinter implements Printer<CatFact> {
    @Override
    public void print(CatFact fact) {
        System.out.println(fact.getFact());
    }
}
