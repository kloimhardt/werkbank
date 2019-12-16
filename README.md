# werkbank

Translates visual Blockly tiles into executable Clojure code.

There is a demo video:

https://kloimhardt.github.io/driven_pendulum.mp4

The video is meant for experts in the domain of theoretical Physics, but muting the sound and watching the tiles moving for one or two minutes gives a good impression. The project runs (and is meant to run) locally in dev mode with figwheel. On pressing the "run" button, a file containing Clojure code is generated and executed, just as if it were typed and saved using a keyboard. So there is no restriction to any specific library, anything can be added to deps.edn and used like in a normal project. At a certain point, a programmer even can forget about Blockly and continue hacking the code in one's favourite editor.

Blockly is very well suited for ready made demonstrations to experts outside of programming, e.g. experts in physics or graphical art or cancer research. Namely to show them how their very own domian can be fruitfully modelled by programmers using Clojure Libraries. Here I demonstarte the use of [SICMutils](https://github.com/littleredcomputer/sicmutils) and (further down) [La Habra](https://github.com/sarahgp/la-habra), but datalog/datahike comes to mind. Here, I do not see Blockly so much as a tool to teach programming in general, imho for this the text-based approach of Racket and Maria-Cloud is best. Blockly (hiding parens!) should merely wet the appetite for problem solving in Clojure. The two way grahical+text approach of Python based BlockPy also shows a way in that direction. In this sense "Blockly/Scratch is for kids" can be read as: they are experts in playing, let's wet their appetite to play in a meaningful way using computers.

The Video demonstration is based on the [SICM book by Sussman  et.al.](https://mitpress.mit.edu/sites/default/files/titles/content/sicm_edition_2/book.html). It shows how to calculate a beast known as Lagrangian Equation using the [SICMUtils Library](https://github.com/littleredcomputer/sicmutils). In addition, a more comprehensive Blockly Workspace for SICMUtils, which also shows the plotting capabilities of the [Vega Library](https://vega.github.io/vega), is provided (see below). 

The second (short) video further down demonstartes the use of the [La Habra Library](https://github.com/sarahgp/la-habra), which was shown (using a keyboard) at the 2019 [Herat of Clojure](https://www.youtube.com/watch?v=F4pozY_RF5c) conference.

## Building the project

to build, enter the following shell command:

    ./startcljblocks.sh

to start the server, enter the following url in browser (ignore "Adress already in use" error messages if any):

localhost:9500/http-kit

## SICMUtils Example

to run a workspace based on the library littleredcomputer/sicmutils, enter the following url in browser:

localhost:3000/abc.html

to open the Sicmutils plotting area:

localhost:3000/vegatex

to open a sample workspace press the "Choose file" button and open "sample_workspaces/workspace_full.xml"


There is also a draft for a paper (in German) "ga_pro_kla_mech.pdf" in the "/docs" directory

## La Habra Example

To run a workspace based on the library sarahgp/la-habra, open "sample_workspaces/workspace_habra.xml" and open the graphics area:

localhost:3000/habra

demo video:

https://kloimhardt.github.io/la_habra_blocks.mp4

(If errors occur during la-habra live reloading, it helps to open dev.cljs.edn and increase :wait-time-ms from  999 to 5000 milliseconds.)
