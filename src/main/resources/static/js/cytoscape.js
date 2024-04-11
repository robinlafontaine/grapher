var cy = cytoscape({
    container: document.getElementById('cy') // container to render in
});

cy.style([ // the stylesheet for the graph
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
            'target-arrow-color': '#ccc',
            'target-arrow-shape': 'triangle',
            'curve-style': 'bezier',
            'label': 'data(weight)'
        }
    }
]);

var collections = [cy.collection([
    { group: 'nodes', data: { id: '1', name: 'Node 1' } },
    { group: 'nodes', data: { id: '2', name: 'Node 2' } },
    { group: 'nodes', data: { id: '3', name: 'Node 3' } },
    { group: 'edges', data: { id: '12', source: '1', target: '2', weight: 1.1 } },
    { group: 'edges', data: { id: '23', source: '2', target: '3', weight: 2.2 } },
    { group: 'edges', data: { id: '31', source: '3', target: '1', weight: 3.3 } }
], {removed: true}), cy.collection([
    { group: 'nodes', data: { id: '4', name: 'Node 4' } },
    { group: 'nodes', data: { id: '5', name: 'Node 5' } },
    { group: 'nodes', data: { id: '6', name: 'Node 6' } },
    { group: 'edges', data: { id: '45', source: '4', target: '5', weight: 1.1 } },
    { group: 'edges', data: { id: '56', source: '5', target: '6', weight: 2.2 } },
    { group: 'edges', data: { id: '64', source: '6', target: '4', weight: 3.3 } }
], {removed: true})];

var eles = cy.add(collections[0]);

cy.layout({
    name: 'random',
});

document.getElementById('reset-button').addEventListener('click', function() {
    console.log("Reset");
    cy.remove(collections[0]);
    eles = cy.add(collections[1]);
    cy.layout({name: 'random'}).run();
});

function changeGraph(json){
    eles.data(json)
}

function addGraph(json){
    cy.add(json)
}
//cy.add([{data: { id: 'caca'}},{data: { id: 'ac', source: 'a', target: 'caca', weight: 6.1}}])

//cy.elements.remove()
//cy.add(collection)
//cy.layout({name: 'circle'}).run()
//cy.autoungrabify(true);
cy.zoom(0.8);
cy.center();

