# Milestone July 25 2019 (dev -> master)
Lots of changes

- Architecture overhaul 
   - split model and controller, model view controller architecture is much clearer
   - proper observer pattern between controllers and Model
   - factory design pattern for creating questions and mode fragments

- Features and practice modes
   - Note, Interval Filter tab fragment for note, interval, triad, graph mode
   - Note Graph, Interval, Triad, Song Practice modes working
   - Summary Page getting started

## BUGS and TODO:
### Song Practice Mode
- (bug) Stop Midi processor when switching mode by clicking on items in navigation menu
- (improve) Song library to select songs (replace current spinner)
- (improve) Make song practice mode and song playing mode synchronous, i.e. switching from song playing mode to song practicing mode, the user is on the note that was in song playing mode, and vice versa
- (improve) Allow user to import their own midi
- (improve) use local storage or cache for tmp files like playing.mid 

### Note Graph mode
- (improve) can display notes in y axis (as an option)

### Summary page
- (todo) notes user should practice more often and notes that user did well
- (fixme) graph in summary page should reflex accuracy and stability of a given note

### PerModeSetting
- (fixme) it's a bit messy right now, can probably simply some complexity in the code


### General
- (fixme) arrow jumping animation, can behave better when slowing down
- (improve) consider adding three arrows for offtrack level
- (todo) misc page for every mode, and their working properly
- (todo) global setting page
- (todo) more page
- (todo) music theory page
- (todo) faq page, ....
- (improve) add a hidden box, or using gesture to next/prev question
- (todo) colour theme

### User experience improvement
- (todo) add comment text box, which will show random encouraging sentences
- (fixme) proper help message for each mode
- (improve) per mode setting page and navigation menu color should be according to each mode
- (improve) app intro page improvement
- (fixme) for phones of small resolution, can display proper text size, and graph, etc.
