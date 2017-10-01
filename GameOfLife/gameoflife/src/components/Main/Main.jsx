import React from 'react';
import Grid from '../Grid/Grid';
import Buttons from '../Buttons/Buttons';

export default class Main extends React.Component {

    constructor() {
        super();

        this.speed = 100;
        this.rows = 30;
        this.cols = 50;

        this.state = {
            generation: 0,
            gridFull: this.fillGrid()
        };
    }

    // Populate grid upon loading
    componentDidMount() {
        this.populateGrid();
        this.playButton();
    }

    fillGrid() {
        return Array(this.rows).fill().map(() => Array(this.cols).fill(false));
    }

    // When a box is clicked change grid fill to the opposite of current status
    selectBox = (row, col) => {
        let gridCopy = arrayClone(this.state.gridFull);
        gridCopy[row][col] = !gridCopy[row][col];

        // not suppose to update state directly, so we create a copy
        this.setState({
            gridFull: gridCopy
        });
    }

    // Populate the grid with clicked (true) cells
    populateGrid = () => {
        let gridCopy = arrayClone(this.state.gridFull);

        for (var i = 0; i < this.rows; i++) {
            for (var j = 0; j < this.cols; j++) {
                if (Math.floor(Math.random() * 4) === 1) {
                    gridCopy[i][j] = true;
                }
            }
        }
        this.setState({
            gridFull: gridCopy
        });
    }

    // Play the Game
    playButton = () => {
        clearInterval(this.intervalId);
        this.intervalId = setInterval(this.play, this.speed);
    }

    // Pause the Game
    pauseButton = () => {
        clearInterval(this.intervalId);
    }

    slow = () => {
        this.speed = 1000;
        this.playButton();
    }

    fast = () => {
        this.speed = 100;
        this.playButton();
    }

    clear = () => {
        let grid = this.fillGrid();
        this.setState({
            gridFull: grid,
            generation: 0
        });
    }

    gridSize = (size) => {
        switch (size) {
            case "1":
                this.cols = 20;
                this.rows = 10;
                break;

            case "2":
                this.cols = 50;
                this.rows = 30;
                break;

            default:
                this.cols = 70;
                this.rows = 50;
                break;
        }

        this.clear();
    }
    
    // Rules for playing Conway's Game of Life
    // https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
    play = () => {
        let g = this.state.gridFull;
        let g2 = arrayClone(this.state.gridFull);
        
            for (let i = 0; i < this.rows; i++) {
                for (let j =  0; j < this.cols; j++) {
                let count = 0;

                        if (i > 0)
                            if (g[i - 1][j])
                        count++;
        
            if (i > 0 && j > 0)
                if (g[i - 1][j - 1])
                        count++;
        
            if (i > 0 && j < this.cols - 1)
                if (g[i - 1][j + 1])
                        count++;
        
            if (j < this.cols - 1)
                if (g[i][j + 1])
                        count++;
        
            if (j > 0)
                if (g[i][j - 1])
                        count++;
        
            if (i < this.rows - 1)
                if (g[i + 1][j])
                        count++;
        
            if (i < this.rows - 1 && j > 0)
                    if (g[i + 1][j - 1])
                count++;
            
        if (i < this.rows - 1 && this.cols - 1)
            if (g[i + 1][j + 1])
                        count++;

        if (g[i][j] && (count < 2 || count > 3))
            g2[i][j] = false;

                if (!g[i][j] && count === 3)
                    g2[i][j] = true;
        }
    }
        
            this.setState({
            gridFull: g2,
                generation: this.state.generation + 1
        });
            }
            
            render() {    
        return (
            <div>
                <h1>The Game of Life</h1>
                <Buttons
                    playButton={this.playButton}
                    pauseButton={this.pauseButton}
                    slow={this.slow}
                    fast={this.fast}
                    clear={this.clear}
                    populateGrid={this.populateGrid}
                    gridSize={this.gridSize}
                    />
                <Grid
                    gridFull={this.state.gridFull}
                    rows={this.rows}
                    cols={this.cols}
                    selectBox={this.selectBox}
                />
                <h2>Generations: {this.state.generation}</h2>
            </div>
        );
    }
}

// helper function to copy array
// stringify and then parse to make a clone of array
// if it wasn't nested we could have done array.slice
function arrayClone(arr) {
    return JSON.parse(JSON.stringify(arr));
}