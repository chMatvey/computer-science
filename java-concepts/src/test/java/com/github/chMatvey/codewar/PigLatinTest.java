package com.github.chMatvey.codewar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PigLatinTest {

    @Test
    void pigIt() {
        assertEquals("igPay atinlay siay oolcay", PigLatin.pigIt("Pig latin is cool"));
        assertEquals("hisTay siay ymay tringsay", PigLatin.pigIt("This is my string"));
        assertEquals("elloHay orldway !", PigLatin.pigIt("Hello world !"));

        assertEquals("igPay atinlay siay oolcay", PigLatin.pigIt2("Pig latin is cool"));
        assertEquals("hisTay siay ymay tringsay", PigLatin.pigIt2("This is my string"));
        assertEquals("elloHay orldway !", PigLatin.pigIt2("Hello world !"));
    }
}