#include <bits/stdc++.h>
#include<queue>
using namespace std;

class node{
    public:
        int data;
        node*left;
        node*right;

        node(int d){
            data = d;
            left = NULL;
            right = NULL;
        }
};

//Accepts the old root node & data and returns the new root node 
node* insertInBST(node *root,int data){
    //Base Case
    if(root==NULL){
        return new node(data);
    }
    //Rec Case - Insert in the Subtree and Update Pointers
    if(data<=root->data){
        root->left = insertInBST(root->left,data);
    }
    else{
        root->right = insertInBST(root->right,data);
    }
    return root;
}

bool search(node*root,int data){
    if(root==NULL){
        return false;
    }
    if(root->data==data){
        return true;
    }
    //Recursively search in left and right subtree
    if(data<=root->data){
        return search(root->left,data);
    }
    else{
        return search(root->right,data);
    }
}

node* build(){
    //Read a list of numbers till -1 and also these numbers will be inserted into BST
    int d;
    cin>>d;

    node*root = NULL;

    while(d!=-1){
        root = insertInBST(root,d);
        cin>>d;
    }
    return root;
}
//Print the BST Level By Level
void bfs(node *root){
    queue<node*> q;
    q.push(root);
   // q.push(NULL);

    while(!q.empty()){
        node* f = q.front();
        // if(f==NULL){
        //     cout<<endl;
        //     q.pop();
        //     if(!q.empty()){
        //         q.push(NULL);
        //     }
        // }
        //else{
            cout<<f->data<<",";
            q.pop();

            if(f->left){
                q.push(f->left);
            }
            if(f->right){
                q.push(f->right);
            }
        //}
    }
    return;
}
//Inorder Print
void inorder(node*root){
    if(root==NULL){
        return;
    }
    inorder(root->left);
    cout<<root->data<<",";
    inorder(root->right);
}

node* deleteInBST(node*root,int data){
    if(root==NULL){
        return NULL;
    }
    else if(data<root->data){
        root->left = deleteInBST(root->left,data);
        return root; 
    }
    else if(data==root->data){
        //Found the node to delete 3 Cases
        //1. Node with 0 children - Leaf Node
        if(root->left==NULL && root->right==NULL){
            delete root;
            return NULL;
        }
        //2. Case Only 1 child
            if(root->left!=NULL && root->right==NULL){
                node* temp = root->left;
                delete root;
                return temp;
            }
            if(root->right!=NULL && root->left==NULL){
                node* temp = root->right;
                delete root;
                return temp;
            }
        //3. Case 2 children
            node *replace = root->right;
            //Find the inorder successor from right subtree
            while(replace->left!=NULL){
                replace = replace->left;
            }
            root->data = replace->data;
            root->right = deleteInBST(root->right,replace->data);
            return root;
    }
    else{
        root->right = deleteInBST(root->right,data);
        return root;
    }
}

 multimap<int,int> map1;
   void verticalOrderTraversal(node * root) {
        if (root==NULL){
            //cout<<"check";
            return;
        }
        queue<pair<node*,int> > q;
        q.push(make_pair(root,0));
        while(!q.empty()){
            node* f = q.front().first;
            int dist = q.front().second;
            q.pop();
            map1.insert(make_pair(dist, f->data));
            if(f->left){
                q.push(make_pair(f->left, dist-1));
            }
            if(f->right){
                q.push(make_pair(f->right, dist+1));
                
            }
        }
    }

int main(){ 
    //node*root = build();
    // inorder(root);
    // cout<<endl;
    
    // int s;
    // cin>>s;
    // root = deleteInBST(root,s);
    // inorder(root);
    // cout<<endl;
    // bfs(root);

    node* root = new node(1);
    root->left = new node(2);
    root->right = new node(3);
    root->right->left = new node(5);
    root->right->right = new node(6);
    root->right->left->left = new node(7);
    root->right->left->right = new node(8);
    root->right->left->right->left = new node(9);
    root->right->left->right->right = new node(10);

    verticalOrderTraversal(root);

    int val=0;
    for(auto itr: map1){
        if(val!=(itr.first)){
            cout<<endl;
            val = itr.first;
        }
        cout<<itr.second<<" ";
    }


    
return 0;
}










