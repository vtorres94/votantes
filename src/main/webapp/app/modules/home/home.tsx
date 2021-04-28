import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';
import { Segment, Image, Grid, Card, Icon, Button } from 'semantic-ui-react';
import { TextFormat } from 'react-jhipster';

export interface IHomeProp extends StateProps, RouteComponentProps<{}> {}

export interface IHomeState {
  defensorFocus: boolean;
  votanteFocus: boolean;
}

export const Home = (props: IHomeProp) => {
  const { account } = props;
  const [state, setState] = useState<IHomeState>({
    defensorFocus: false,
    votanteFocus: false,
  });
  return (
    <Row>
      {!account.login ? (
        <Col md="12" className="pad">
          <span className="hipster rounded" />
        </Col>
      ) : null}
      <Col md="12" style={{ textAlign: 'center' }}>
        <h2>Bienvenido al registro de activistas</h2>
        <p className="lead">Elecciones 2020 - 2021</p>
        {account && account.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
            <Segment>
              <Grid columns={2} textAlign="center">
                <Grid.Column>
                  <Card
                    href="/c-defensor-voto"
                    centered
                    onMouseEnter={() => setState({ ...state, defensorFocus: true })}
                    onMouseLeave={() => setState({ ...state, defensorFocus: false })}
                    color={!state.defensorFocus ? 'red' : 'blue'}
                  >
                    <Image src="./content/images/sword.png" wrapped ui={false} />
                    <Card.Content>
                      <Card.Header>Defensores del voto</Card.Header>
                      <Card.Meta>2020 - 2021</Card.Meta>
                    </Card.Content>
                    <Card.Content extra>
                      <a>
                        <Icon name="user" />
                        10 Friends
                      </a>
                    </Card.Content>
                  </Card>
                </Grid.Column>
                <Grid.Column>
                  <Card
                    href="/c-votante"
                    centered
                    onMouseEnter={() => setState({ ...state, votanteFocus: true })}
                    onMouseLeave={() => setState({ ...state, votanteFocus: false })}
                    color={!state.votanteFocus ? 'red' : 'blue'}
                  >
                    <Image src="./content/images/votante.png" wrapped ui={false} />
                    <Card.Content>
                      <Card.Header>Votantes</Card.Header>
                      <Card.Meta>2020 - 2021</Card.Meta>
                    </Card.Content>
                    <Card.Content extra>
                      <a>
                        <Icon name="user" />
                        10 Friends
                      </a>
                    </Card.Content>
                  </Card>
                </Grid.Column>
              </Grid>
            </Segment>
          </div>
        ) : (
          <div>
            <Segment textAlign="center">
              <Button color="blue" onClick={() => props.history.push('/login')}>
                Iniciar sesión
              </Button>
              <Button color="green" onClick={() => props.history.push('/account/register')}>
                Registrarse
              </Button>
            </Segment>
          </div>
        )}
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
