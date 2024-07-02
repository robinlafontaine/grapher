var cy = cytoscape({
    container: document.getElementById('cy') // container to render in
});

cy.style([
    {
        selector: 'node',
        style: {
            'background-color': '#666',
            'label': 'data(name)'
        }
    },

    {
        selector: 'edge',
        style: {
            'width': 3,
            'line-color': '#ccc',
            'target-arrow-shape': 'none',
            'curve-style': 'bezier',
            'label': 'data(weight)'
        }
    },

    {
        selector: '.solution_node',
        style: {
            'background-color': '#f00',
        }
    },

    {
        selector: '.solution_edge',
        style: {
            'line-color': '#f00',
        }

    }
]);

cy.zoom(0.5);
cy.layout({name: 'circle'}).run();

function addGraph(json) {
    cy.add(JSON.parse(json));
    cy.layout({name: 'circle'}).run();
}

function findRoute() {
    var from = document.getElementById('from').value;
    var to = document.getElementById('to').value;
    location.href = '/graph?from=' + from + '&to=' + to;
}

//cy.elements.remove()
//cy.add(collection)
//cy.layout({name: 'circle'}).run()
//cy.autoungrabify(true);

