Biomorph Prototype
==================
## Usage
The preferred IDE to use is IntelliJ, however the project has been converted
so that Eclipse may use it too.

The main class is `NewMainWindow`

The working directory must be writable as `biomorphhistory.dat` is written to it.

## .biomorph file format

A .biomorph file is simply a text file that contains the "genome" string of a
biomorph. It can also contain comments, indentation and other formatting. They
should also be encoded in ASCII or UTF-8 only.

Between genes, any non alphanumeric character is ignored. If a hash (#) is seen
the rest of the line will be ignored. Genes cannot be split up, i.e. `D123456`
would be valid, `D 123456` would not be, neither would `D12 34 56`.

There are two special characters in a genome, `S` and `s`. These characters are
used to manipulate the stack used in parsing a biomorph.

`S` will push the last added gene onto the top of the stack. This means that
subsequent genes will be sub genes of that gene.

`s` will pop gene on top of the stack, meaning subsequent genes will be
siblings of the popped gene.

Using this simple language, the tree structure of the biomorph's genome can be
created.

## Genes
Genes are the parts that make up a biomorph. When serialised into a genome
string, they are represented by a character, for example `D` for Dot (a circle),
followed by a required number of hexadecimal digits.

The Dot gene, for example, requires 6 hexadecimal digits. The first two
represent width, the next two height, and the last two angle.

A Dot gene could look like this:
    D112233

## Available genes
### Dot (D)
A dot is simply a circle, it requires 6 hexadecimal digits to set its width,
height and angle.

#### Example
    D112233

### Line (L)
Lines require 6 hex digitsthat represent length, thickness and angle.

#### Example
    L1A2B3C

### Hexagon (H)
Hexagons only require 4 hex digits, they represent the side length and angle.

#### Example
    H5522

### Example .biomorph file
    D112233 # although in the program they appear on only one line, biomorph
            # files may span several
    L223344
    S # S and s don't require any values
      H1234
      S L111111D123456 s  # whitespace is optional
      D222222
    s # it's not necessary to pop at the end, even though the exporter will
      # add them