var cy = cytoscape({

    container: document.getElementById('cy'), // container to render in

    elements: [ // list of graph elements to start with
        { // node a
            data: {id: 'a'}
        },
        { // node b
            data: {id: 'b'}
        },
        { // edge ab
            data: {id: 'ab', source: 'a', target: 'b'}
        }
    ],

    style: [ // the stylesheet for the graph
        {
            selector: 'node',
            style: {
                'background-color': '#666',
                'label': 'data(id)'
            }
        },

        {
            selector: 'edge',
            style: {
                'width': 3,
                'line-color': '#ccc',
                'target-arrow-color': '#ccc',
                'target-arrow-shape': 'triangle',
                'curve-style': 'bezier'
            }
        }
    ],

    layout: {
        name: 'grid',
        rows: 1
    }

});
function addGraph(json){
    cy.add(json)
}
cy.add([{data: { id: 'caca'}},{data: { id: 'ac', source: 'a', target: 'caca', weight: 6.1}}])

cy.autoungrabify(true);
cy.zoom(0.8);
cy.center();

