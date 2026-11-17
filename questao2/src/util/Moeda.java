package util;

import java.math.BigDecimal;
import java.util.Locale;

/** Formata valores monetários no padrão brasileiro (R$ 1.234,56). */
public final class Moeda {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private Moeda() {
    }

    public static String formatar(BigDecimal valor) {
        return String.format(PT_BR, "R$ %,.2f", valor);
    }
}
