# werkbank

Look at the demo video:

https://kloimhardt.github.io/driven_pendulum.mp4

to build, enter the following shell command:

    ./startcljblocks.sh

to start the server, enter the following url in browser (ignore "Adress already in use" error messages if any):

localhost:9500/http-kit

## SICMUtils Example

to run a workspace based on the library littleredcomputer/sicmutils, enter the following url in browser:

localhost:3000/abc.html

to open the Sicmutils plotting area:

localhost:3000/vegatex

to open a sample in the workspace press the "Choose file" button and open "sample_workspaces/workspace_full.xml"


There is also a draft for a paper (in German) "ga_pro_kla_mech.pdf" in the "/docs" directory

## La Habra Example

To run a workspace based on the library sarahgp/la-habra, open "sample_workspaces/workspace_habra.xml" and open the graphics area:

localhost:3000/habra

demo video:

https://kloimhardt.github.io/la_habra_blocks.mp4

(If errors occur during la-habra live reloading, it helps to open dev.cljs.edn and increase :wait-time-ms from  999 to 5000 milliseconds.)
